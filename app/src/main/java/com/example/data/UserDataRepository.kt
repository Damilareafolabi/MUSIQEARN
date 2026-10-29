package com.example.data

import com.example.model.ArtistSubmission
import com.example.model.SubmissionStatus
import com.example.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserDataRepository {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _submissions = MutableStateFlow<List<ArtistSubmission>>(
        listOf(
            ArtistSubmission(
                id = "sub_demo_1",
                artistName = "Afolabi Simeon (A.K.A SimeonJr)",
                songTitle = "Lagos To The World",
                genre = "Afro-Fusion / Instrumental",
                releaseYear = "2024",
                streamingLink = "https://audiomack.com/simeonjr",
                rightsOwnershipConfirmed = true,
                status = SubmissionStatus.APPROVED_LIVE,
                submittedDate = "2024-10-20"
            )
        )
    )
    val submissions: StateFlow<List<ArtistSubmission>> = _submissions.asStateFlow()

    private val _claimedRewardIds = MutableStateFlow<Set<String>>(emptySet())
    val claimedRewardIds: StateFlow<Set<String>> = _claimedRewardIds.asStateFlow()

    fun recordGameResult(pointsEarned: Int, won: Boolean) {
        _userProfile.update { current ->
            val newXp = current.xp + pointsEarned
            val newLevel = (newXp / 500) + 1
            val coinsReward = if (won) (pointsEarned / 20) + 10 else (pointsEarned / 50)
            val newStreak = if (won) current.currentStreak + 1 else 0
            val bestStreak = maxOf(current.bestStreak, newStreak)

            current.copy(
                xp = newXp,
                level = newLevel,
                musizCoins = current.musizCoins + coinsReward,
                currentStreak = newStreak,
                bestStreak = bestStreak,
                totalGamesPlayed = current.totalGamesPlayed + 1,
                totalWins = if (won) current.totalWins + 1 else current.totalWins
            )
        }
    }

    fun submitArtistTrack(
        artistName: String,
        songTitle: String,
        genre: String,
        releaseYear: String,
        streamingLink: String,
        rightsConfirmed: Boolean
    ): Boolean {
        if (!rightsConfirmed) return false
        val newSub = ArtistSubmission(
            id = "sub_${System.currentTimeMillis()}",
            artistName = artistName,
            songTitle = songTitle,
            genre = genre,
            releaseYear = releaseYear,
            streamingLink = streamingLink,
            rightsOwnershipConfirmed = true,
            status = SubmissionStatus.PENDING_REVIEW,
            submittedDate = "Just now"
        )
        _submissions.update { listOf(newSub) + it }
        return true
    }

    fun claimReward(campaignId: String): Boolean {
        if (_claimedRewardIds.value.contains(campaignId)) return false
        _claimedRewardIds.update { it + campaignId }
        return true
    }

    fun updateCountry(countryName: String, flag: String) {
        _userProfile.update { it.copy(country = countryName, countryFlag = flag) }
    }
}
