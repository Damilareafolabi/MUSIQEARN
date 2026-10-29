package com.example.data

import android.content.Context
import android.util.Log
import com.example.R
import com.example.model.ArtistSubmission
import com.example.model.LeaderboardEntry
import com.example.model.SubmissionStatus
import com.example.model.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

@IgnoreExtraProperties
data class FirestoreUserDoc(
    @DocumentId val id: String = "",
    val userId: String = "",
    val username: String = "",
    val country: String = "Nigeria",
    val countryFlag: String = "🇳🇬",
    val level: Long = 1L,
    val xp: Long = 0L,
    val musizCoins: Long = 50L,
    val currentStreak: Long = 0L,
    val bestStreak: Long = 0L,
    val totalGamesPlayed: Long = 0L,
    val totalWins: Long = 0L,
    val rankTitle: String = "Music Novice",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreLeaderboardDoc(
    @DocumentId val id: String = "",
    val userId: String = "",
    val username: String = "",
    val country: String = "Nigeria",
    val flag: String = "🇳🇬",
    val score: Long = 0L,
    val streak: Long = 0L,
    val badge: String = "🏆",
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreBattleDoc(
    @DocumentId val id: String = "",
    val battleId: String = "",
    val creatorUserId: String = "",
    val creatorUsername: String = "",
    val creatorScore: Long = 0L,
    val opponentUserId: String = "",
    val opponentUsername: String = "",
    val opponentScore: Long = 0L,
    val status: String = "WAITING", // WAITING, IN_PROGRESS, COMPLETED
    val inviteCode: String = "",
    val winnerId: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

class FirestoreRepository(
    private val db: FirebaseFirestore
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    fun currentUserId(): String? = auth.currentUser?.uid

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google to access Firestore.")
    }

    /**
     * Real-time stream of the current authenticated user's profile
     */
    fun observeUserProfile(userId: String): Flow<UserProfile?> {
        return db.collection("users").document(userId)
            .snapshots()
            .map { snapshot ->
                if (!snapshot.exists()) return@map null
                val doc = snapshot.toObject(FirestoreUserDoc::class.java) ?: return@map null
                UserProfile(
                    username = doc.username,
                    country = doc.country,
                    countryFlag = doc.countryFlag,
                    level = doc.level.toInt(),
                    xp = doc.xp.toInt(),
                    xpNextLevel = (doc.level.toInt() * 500) + 500,
                    musizCoins = doc.musizCoins.toInt(),
                    currentStreak = doc.currentStreak.toInt(),
                    bestStreak = doc.bestStreak.toInt(),
                    totalGamesPlayed = doc.totalGamesPlayed.toInt(),
                    totalWins = doc.totalWins.toInt(),
                    rankTitle = doc.rankTitle
                )
            }
            .catch { e ->
                Log.e("FirestoreRepo", "Error observing profile: ${e.message}", e)
                emit(null)
            }
    }

    /**
     * Creates or initializes the user profile in Firestore if not existing
     */
    suspend fun createOrSyncUser(googleDisplayName: String?, email: String?): UserProfile {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid)
        val snapshot = docRef.get().await()

        if (snapshot.exists()) {
            val doc = snapshot.toObject(FirestoreUserDoc::class.java)!!
            return UserProfile(
                username = doc.username,
                country = doc.country,
                countryFlag = doc.countryFlag,
                level = doc.level.toInt(),
                xp = doc.xp.toInt(),
                xpNextLevel = (doc.level.toInt() * 500) + 500,
                musizCoins = doc.musizCoins.toInt(),
                currentStreak = doc.currentStreak.toInt(),
                bestStreak = doc.bestStreak.toInt(),
                totalGamesPlayed = doc.totalGamesPlayed.toInt(),
                totalWins = doc.totalWins.toInt(),
                rankTitle = doc.rankTitle
            )
        } else {
            val defaultName = googleDisplayName?.ifBlank { null } ?: "Player_${uid.take(5)}"
            val initial = FirestoreUserDoc(
                userId = uid,
                username = defaultName,
                country = "Nigeria",
                countryFlag = "🇳🇬",
                level = 1L,
                xp = 100L,
                musizCoins = 100L,
                currentStreak = 1L,
                bestStreak = 1L,
                totalGamesPlayed = 0L,
                totalWins = 0L,
                rankTitle = "Afrobeats Novice",
                createdAt = null,
                updatedAt = null
            )
            docRef.set(initial).await()
            return UserProfile(
                username = defaultName,
                country = "Nigeria",
                countryFlag = "🇳🇬",
                level = 1,
                xp = 100,
                xpNextLevel = 1000,
                musizCoins = 100,
                currentStreak = 1,
                bestStreak = 1,
                totalGamesPlayed = 0,
                totalWins = 0,
                rankTitle = "Afrobeats Novice"
            )
        }
    }

    /**
     * Updates player score and syncs to both user profile and public leaderboard
     */
    suspend fun recordGameResult(pointsEarned: Int, won: Boolean, userProfile: UserProfile) {
        val uid = requireUserId()
        val newXp = userProfile.xp + pointsEarned
        val newLevel = (newXp / 500) + 1
        val coinsDelta = if (won) (pointsEarned / 20) + 10 else (pointsEarned / 50)
        val newCoins = userProfile.musizCoins + coinsDelta
        val newStreak = if (won) userProfile.currentStreak + 1 else 0
        val bestStreak = maxOf(userProfile.bestStreak, newStreak)
        val totalGames = userProfile.totalGamesPlayed + 1
        val totalWins = if (won) userProfile.totalWins + 1 else userProfile.totalWins

        // Update User Doc
        val userRef = db.collection("users").document(uid)
        userRef.update(
            mapOf(
                "xp" to newXp.toLong(),
                "level" to newLevel.toLong(),
                "musizCoins" to newCoins.toLong(),
                "currentStreak" to newStreak.toLong(),
                "bestStreak" to bestStreak.toLong(),
                "totalGamesPlayed" to totalGames.toLong(),
                "totalWins" to totalWins.toLong(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
        ).await()

        // Sync to Public Leaderboards
        val leaderboardRef = db.collection("leaderboard").document(uid)
        leaderboardRef.set(
            mapOf(
                "userId" to uid,
                "username" to userProfile.username,
                "country" to userProfile.country,
                "flag" to userProfile.countryFlag,
                "score" to newXp.toLong(),
                "streak" to newStreak.toLong(),
                "badge" to if (won) "⚡" else "🎵",
                "updatedAt" to FieldValue.serverTimestamp()
            )
        ).await()
    }

    /**
     * Real-time stream of the top global leaderboard players
     */
    fun observeLeaderboard(): Flow<List<LeaderboardEntry>> {
        return db.collection("leaderboard")
            .orderBy("score", Query.Direction.DESCENDING)
            .limit(20)
            .snapshots()
            .map { querySnapshot ->
                querySnapshot.documents.mapIndexed { index, doc ->
                    val data = doc.toObject(FirestoreLeaderboardDoc::class.java)
                    LeaderboardEntry(
                        rank = index + 1,
                        username = data?.username ?: "Player",
                        country = data?.country ?: "Global",
                        flag = data?.flag ?: "🌍",
                        score = data?.score?.toInt() ?: 0,
                        streak = data?.streak?.toInt() ?: 0,
                        isUser = data?.userId == auth.currentUser?.uid,
                        badge = when (index) {
                            0 -> "👑"
                            1 -> "🥈"
                            2 -> "🥉"
                            else -> data?.badge ?: "⚡"
                        }
                    )
                }
            }
            .catch { e ->
                Log.e("FirestoreRepo", "Error observing leaderboard: ${e.message}", e)
                emit(emptyList())
            }
    }

    /**
     * Submits an artist track for human rights moderation in Firestore
     */
    suspend fun submitArtistSong(
        artistName: String,
        songTitle: String,
        genre: String,
        releaseYear: String,
        streamingLink: String,
        rightsConfirmed: Boolean
    ): Boolean {
        if (!rightsConfirmed) return false
        val uid = requireUserId()
        val subId = "sub_${System.currentTimeMillis()}"
        val docRef = db.collection("artist_submissions").document(subId)

        val payload = mapOf(
            "id" to subId,
            "userId" to uid,
            "artistName" to artistName,
            "songTitle" to songTitle,
            "genre" to genre,
            "releaseYear" to releaseYear,
            "streamingLink" to streamingLink,
            "rightsOwnershipConfirmed" to true,
            "status" to "PENDING_REVIEW",
            "submittedDate" to "Just now",
            "createdAt" to FieldValue.serverTimestamp()
        )
        docRef.set(payload).await()
        return true
    }

    /**
     * Observes artist submissions submitted by the user
     */
    fun observeUserSubmissions(): Flow<List<ArtistSubmission>> {
        val uid = auth.currentUser?.uid ?: return flow { emit(emptyList()) }
        return db.collection("artist_submissions")
            .whereEqualTo("userId", uid)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map { doc ->
                    ArtistSubmission(
                        id = doc.getString("id") ?: doc.id,
                        artistName = doc.getString("artistName") ?: "Unknown",
                        songTitle = doc.getString("songTitle") ?: "Untitled",
                        genre = doc.getString("genre") ?: "Afrobeats",
                        releaseYear = doc.getString("releaseYear") ?: "2024",
                        streamingLink = doc.getString("streamingLink") ?: "",
                        rightsOwnershipConfirmed = doc.getBoolean("rightsOwnershipConfirmed") ?: true,
                        status = when (doc.getString("status")) {
                            "APPROVED_LIVE" -> SubmissionStatus.APPROVED_LIVE
                            "REJECTED" -> SubmissionStatus.REJECTED
                            else -> SubmissionStatus.PENDING_REVIEW
                        },
                        submittedDate = doc.getString("submittedDate") ?: "Recently"
                    )
                }
            }
            .catch { e ->
                Log.e("FirestoreRepo", "Error observing submissions: ${e.message}", e)
                emit(emptyList())
            }
    }
}
