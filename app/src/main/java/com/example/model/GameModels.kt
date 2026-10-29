package com.example.model

enum class GameMode(val title: String, val subtitle: String, val iconName: String) {
    GUESS_SONG("Guess the Song", "Listen to the preview & name the song title", "music_note"),
    NAME_ARTIST("Name the Artist", "Identify the hitmaker behind the groove", "mic"),
    MUSIC_BATTLE("1v1 Music Battle", "Head-to-head showdown with live scoring", "sports_esports"),
    DAILY_CHALLENGE("Daily Challenge", "5 curated tracks to keep your streak alive", "local_fire_department"),
    MISSING_LYRICS("Missing Lyrics", "Fill in the blank of authorized song lines", "lyrics"),
    GENRE_TRIVIA("Genre & Rhythm", "Identify tempos, instruments & music roots", "graphic_eq")
}

data class SongQuestion(
    val id: String,
    val title: String,
    val artist: String,
    val genre: String,
    val year: String,
    val snippetRiffId: String,
    val options: List<String>,
    val correctIndex: Int,
    val lyricsSnippet: String = "",
    val funFact: String,
    val rightsAttribution: String = "Authorized development melody synthesis / legal metadata attribution"
)

enum class BattleStatus {
    MATCHMAKING,
    IN_PROGRESS,
    ROUND_FINISHED,
    MATCH_COMPLETE
}

data class BattleState(
    val round: Int = 1,
    val totalRounds: Int = 5,
    val playerScore: Int = 0,
    val opponentScore: Int = 0,
    val opponentName: String = "AfroBeatsKing_99",
    val opponentCountry: String = "🇳🇬 Nigeria",
    val status: BattleStatus = BattleStatus.IN_PROGRESS,
    val currentQuestion: SongQuestion? = null
)

data class EmergingArtist(
    val id: String,
    val stageName: String,
    val realName: String,
    val country: String,
    val flag: String,
    val genre: String,
    val bio: String,
    val verified: Boolean = true,
    val followerCount: String,
    val featuredTrackTitle: String,
    val snippetRiffId: String,
    val streamSpotifyUrl: String,
    val streamAppleUrl: String,
    val streamAudiomackUrl: String,
    val streamYoutubeUrl: String,
    val activeChallengesCount: Int
)

enum class RewardType(val label: String, val unit: String) {
    AIRTIME("Mobile Airtime", "Airtime"),
    DATA_BUNDLE("Data Bundle", "GB"),
    CASH_PRIZE("Cash Tournament", "Cash"),
    MERCH_CONCERT("VIP Passes & Merch", "VIP")
}

data class RewardCampaign(
    val id: String,
    val title: String,
    val sponsorName: String,
    val rewardType: RewardType,
    val prizeValue: String,
    val totalPool: String,
    val remainingBudget: String,
    val country: String,
    val minScoreToQualify: Int,
    val daysRemaining: Int,
    val isLive: Boolean,
    val sponsorDisclaimer: String,
    val verificationNotice: String
)

data class UserProfile(
    val username: String = "StarPlayer_Simeon",
    val country: String = "Nigeria",
    val countryFlag: String = "🇳🇬",
    val level: Int = 4,
    val xp: Int = 1850,
    val xpNextLevel: Int = 2500,
    val musizCoins: Int = 340,
    val currentStreak: Int = 5,
    val bestStreak: Int = 14,
    val totalGamesPlayed: Int = 42,
    val totalWins: Int = 29,
    val rankTitle: String = "Afrobeats Prodigy"
)

data class LeaderboardEntry(
    val rank: Int,
    val username: String,
    val country: String,
    val flag: String,
    val score: Int,
    val streak: Int,
    val isUser: Boolean = false,
    val badge: String = "🏆"
)

enum class SubmissionStatus {
    PENDING_REVIEW,
    APPROVED_LIVE,
    REJECTED
}

data class ArtistSubmission(
    val id: String,
    val artistName: String,
    val songTitle: String,
    val genre: String,
    val releaseYear: String,
    val streamingLink: String,
    val rightsOwnershipConfirmed: Boolean,
    val status: SubmissionStatus = SubmissionStatus.PENDING_REVIEW,
    val submittedDate: String
)
