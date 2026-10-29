package com.example.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.MusicAudioEngine
import com.example.data.FirestoreRepository
import com.example.data.LeaderboardData
import com.example.data.PlatformData
import com.example.data.RewardsData
import com.example.data.SampleMusicDatabase
import com.example.data.UserDataRepository
import com.example.data.ExpansionData
import com.example.model.AcademyCertificate
import com.example.model.AcademyCourse
import com.example.model.BeatLicenseTransaction
import com.example.model.BeatLicenseType
import com.example.model.BusinessEnquiry
import com.example.model.BusinessListing
import com.example.model.CreatorServiceListing
import com.example.model.ProducerBeat
import com.example.model.RegisterBusinessRequest
import com.example.model.ServiceQuoteRequest
import com.example.model.SoundAsset
import com.example.model.AdminReport
import com.example.model.ArtistSubmission
import com.example.model.BattleState
import com.example.model.BattleStatus
import com.example.model.BookingRequest
import com.example.model.BookingStatus
import com.example.model.CelebrityBookingProfile
import com.example.model.CelebrityFanClub
import com.example.model.CrowdfundingCampaign
import com.example.model.DirectChatMessage
import com.example.model.EmergingArtist
import com.example.model.FanClubPost
import com.example.model.GameMode
import com.example.model.LeaderboardEntry
import com.example.model.RewardCampaign
import com.example.model.SongQuestion
import com.example.model.SubmissionStatus
import com.example.model.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class Screen {
    HOME,
    GAMEPLAY,
    BATTLE,
    LEADERBOARDS,
    DISCOVER,
    REWARDS,
    ARTIST_PORTAL,
    PROFILE,
    FAN_CLUBS,
    CROWDFUNDING,
    BOOKINGS,
    ADMIN_PORTAL,
    BUSINESS_HUB,
    PRODUCER_ZONE,
    ACADEMY,
    SOUND_LIBRARY,
    CREATOR_SERVICES
}

enum class AnswerState {
    NONE,
    CORRECT,
    WRONG,
    TIMEOUT
}

data class QuizSessionState(
    val mode: GameMode = GameMode.GUESS_SONG,
    val questions: List<SongQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val streak: Int = 0,
    val timeRemainingSeconds: Int = 15,
    val selectedOptionIndex: Int = -1,
    val answerState: AnswerState = AnswerState.NONE,
    val isFinished: Boolean = false,
    val funFactRevealed: String = ""
)

class MusizEarnViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val localRepository = UserDataRepository()
    private val firestoreRepository: FirestoreRepository? = try {
        FirestoreRepository(application.applicationContext)
    } catch (_: Exception) {
        null
    }

    val audioEngine = MusicAudioEngine(viewModelScope)

    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _userProfile = MutableStateFlow(localRepository.userProfile.value)
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _submissions = MutableStateFlow(localRepository.submissions.value)
    val submissions: StateFlow<List<ArtistSubmission>> = _submissions.asStateFlow()

    val claimedRewardIds = localRepository.claimedRewardIds

    private val _isAuthenticated = MutableStateFlow(Firebase.auth.currentUser != null)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _cloudLeaderboards = MutableStateFlow<List<LeaderboardEntry>>(emptyList())

    private val _quizState = MutableStateFlow(QuizSessionState())
    val quizState: StateFlow<QuizSessionState> = _quizState.asStateFlow()

    private val _battleState = MutableStateFlow(BattleState())
    val battleState: StateFlow<BattleState> = _battleState.asStateFlow()

    private val _friendInviteCode = MutableStateFlow("MUSIZ-AFRO-${Random.nextInt(1000, 9999)}")
    val friendInviteCode: StateFlow<String> = _friendInviteCode.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _selectedLeaderboardTab = MutableStateFlow("Global")
    val selectedLeaderboardTab: StateFlow<String> = _selectedLeaderboardTab.asStateFlow()

    private var timerJob: Job? = null
    private var battleTimerJob: Job? = null
    private var profileObserverJob: Job? = null
    private var submissionObserverJob: Job? = null

    private val _currentTrack = MutableStateFlow<SongQuestion?>(SampleMusicDatabase.questions.firstOrNull())
    val currentTrack: StateFlow<SongQuestion?> = _currentTrack.asStateFlow()

    private val _isFullPlayerExpanded = MutableStateFlow(false)
    val isFullPlayerExpanded: StateFlow<Boolean> = _isFullPlayerExpanded.asStateFlow()

    private val _fanClubs = MutableStateFlow(PlatformData.fanClubs)
    val fanClubs: StateFlow<List<CelebrityFanClub>> = _fanClubs.asStateFlow()

    private val _crowdfundingCampaigns = MutableStateFlow(PlatformData.crowdfundingCampaigns)
    val crowdfundingCampaigns: StateFlow<List<CrowdfundingCampaign>> = _crowdfundingCampaigns.asStateFlow()

    private val _celebrityBookings = MutableStateFlow(PlatformData.celebrityBookings)
    val celebrityBookings: StateFlow<List<CelebrityBookingProfile>> = _celebrityBookings.asStateFlow()

    private val _bookingRequests = MutableStateFlow(PlatformData.sampleBookingRequests)
    val bookingRequests: StateFlow<List<BookingRequest>> = _bookingRequests.asStateFlow()

    private val _communityChatMessages = MutableStateFlow(PlatformData.communityChatMessages)
    val communityChatMessages: StateFlow<List<DirectChatMessage>> = _communityChatMessages.asStateFlow()

    private val _adminReports = MutableStateFlow(PlatformData.adminReports)
    val adminReports: StateFlow<List<AdminReport>> = _adminReports.asStateFlow()

    // --- EXPANSION MODULE STATE FLOWS ---
    private val _businesses = MutableStateFlow(ExpansionData.defaultBusinesses)
    val businesses: StateFlow<List<BusinessListing>> = _businesses.asStateFlow()

    private val _businessEnquiries = MutableStateFlow<List<BusinessEnquiry>>(emptyList())
    val businessEnquiries: StateFlow<List<BusinessEnquiry>> = _businessEnquiries.asStateFlow()

    private val _beats = MutableStateFlow(ExpansionData.defaultBeats)
    val beats: StateFlow<List<ProducerBeat>> = _beats.asStateFlow()

    private val _beatTransactions = MutableStateFlow<List<BeatLicenseTransaction>>(emptyList())
    val beatTransactions: StateFlow<List<BeatLicenseTransaction>> = _beatTransactions.asStateFlow()

    private val _currentlyPlayingBeatId = MutableStateFlow<String?>(null)
    val currentlyPlayingBeatId: StateFlow<String?> = _currentlyPlayingBeatId.asStateFlow()

    private val _academyCourses = MutableStateFlow(ExpansionData.defaultAcademyCourses)
    val academyCourses: StateFlow<List<AcademyCourse>> = _academyCourses.asStateFlow()

    private val _completedLessons = MutableStateFlow<Set<String>>(setOf("les-01"))
    val completedLessons: StateFlow<Set<String>> = _completedLessons.asStateFlow()

    private val _academyCertificates = MutableStateFlow<List<AcademyCertificate>>(emptyList())
    val academyCertificates: StateFlow<List<AcademyCertificate>> = _academyCertificates.asStateFlow()

    private val _soundAssets = MutableStateFlow(ExpansionData.defaultSoundAssets)
    val soundAssets: StateFlow<List<SoundAsset>> = _soundAssets.asStateFlow()

    private val _currentlyPlayingSoundId = MutableStateFlow<String?>(null)
    val currentlyPlayingSoundId: StateFlow<String?> = _currentlyPlayingSoundId.asStateFlow()

    private val _creatorServices = MutableStateFlow(ExpansionData.defaultCreatorServices)
    val creatorServices: StateFlow<List<CreatorServiceListing>> = _creatorServices.asStateFlow()

    private val _serviceQuotes = MutableStateFlow<List<ServiceQuoteRequest>>(emptyList())
    val serviceQuotes: StateFlow<List<ServiceQuoteRequest>> = _serviceQuotes.asStateFlow()

    init {
        // Listen to Auth State changes in Firebase
        val authListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            _isAuthenticated.value = user != null
            if (user != null) {
                onUserAuthenticated(user.uid, user.displayName, user.email)
            } else {
                profileObserverJob?.cancel()
                submissionObserverJob?.cancel()
            }
        }
        Firebase.auth.addAuthStateListener(authListener)

        // Observe cloud leaderboards
        firestoreRepository?.let { repo ->
            viewModelScope.launch {
                repo.observeLeaderboard().collectLatest { cloudList ->
                    if (cloudList.isNotEmpty()) {
                        _cloudLeaderboards.value = cloudList
                    }
                }
            }
        }
    }

    private fun onUserAuthenticated(uid: String, displayName: String?, email: String?) {
        viewModelScope.launch {
            firestoreRepository?.let { repo ->
                try {
                    val synced = repo.createOrSyncUser(displayName, email)
                    _userProfile.value = synced

                    profileObserverJob?.cancel()
                    profileObserverJob = viewModelScope.launch {
                        repo.observeUserProfile(uid).collectLatest { updated ->
                            if (updated != null) {
                                _userProfile.value = updated
                            }
                        }
                    }

                    submissionObserverJob?.cancel()
                    submissionObserverJob = viewModelScope.launch {
                        repo.observeUserSubmissions().collectLatest { subs ->
                            if (subs.isNotEmpty()) {
                                _submissions.value = subs
                            }
                        }
                    }
                    showToast("Signed in as ${displayName ?: "Player"}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun setFullPlayerExpanded(expanded: Boolean) {
        _isFullPlayerExpanded.value = expanded
    }

    fun playTrack(track: SongQuestion) {
        _currentTrack.value = track
        audioEngine.playMusicSnippet(
            riffId = track.snippetRiffId,
            title = "${track.title} - ${track.artist}",
            durationSec = 20
        )
        showToast("Playing ${track.title}")
    }

    fun togglePlayback() {
        val track = _currentTrack.value ?: SampleMusicDatabase.questions.firstOrNull() ?: return
        if (audioEngine.isPlaying.value) {
            audioEngine.stop()
        } else {
            playTrack(track)
        }
    }

    fun nextTrack() {
        val all = SampleMusicDatabase.questions
        val current = _currentTrack.value
        val idx = all.indexOfFirst { it.id == current?.id }
        val nextIdx = (idx + 1) % all.size
        playTrack(all[nextIdx])
    }

    fun prevTrack() {
        val all = SampleMusicDatabase.questions
        val current = _currentTrack.value
        val idx = all.indexOfFirst { it.id == current?.id }
        val prevIdx = if (idx <= 0) all.size - 1 else idx - 1
        playTrack(all[prevIdx])
    }

    fun startQuizForTrack(track: SongQuestion) {
        _isFullPlayerExpanded.value = false
        val all = SampleMusicDatabase.questions
        val reordered = listOf(track) + all.filter { it.id != track.id }.take(4)
        _quizState.value = QuizSessionState(
            mode = GameMode.GUESS_SONG,
            questions = reordered,
            currentIndex = 0,
            score = 0,
            streak = 0,
            timeRemainingSeconds = 15,
            selectedOptionIndex = -1,
            answerState = AnswerState.NONE,
            isFinished = false,
            funFactRevealed = ""
        )
        navigateTo(Screen.GAMEPLAY)
        playCurrentQuestion()
    }

    fun navigateTo(screen: Screen) {
        _isFullPlayerExpanded.value = false
        if (screen == Screen.GAMEPLAY || screen == Screen.BATTLE) {
            audioEngine.stop()
        }
        _currentScreen.value = screen
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
        viewModelScope.launch {
            delay(3000)
            if (_toastMessage.value == msg) {
                _toastMessage.value = null
            }
        }
    }

    fun toggleAudioMute() {
        audioEngine.isMuted = !audioEngine.isMuted
        if (audioEngine.isMuted) {
            audioEngine.stop()
            showToast("Audio Muted")
        } else {
            showToast("Audio Enabled")
        }
    }

    // --- Quiz Engine ---

    fun startQuiz(mode: GameMode) {
        val qList = SampleMusicDatabase.getQuestionsForMode(mode, 5)
        _quizState.value = QuizSessionState(
            mode = mode,
            questions = qList,
            currentIndex = 0,
            score = 0,
            streak = 0,
            timeRemainingSeconds = 15,
            selectedOptionIndex = -1,
            answerState = AnswerState.NONE,
            isFinished = false,
            funFactRevealed = ""
        )
        navigateTo(Screen.GAMEPLAY)
        playCurrentQuestion()
    }

    private fun playCurrentQuestion() {
        timerJob?.cancel()
        val state = _quizState.value
        if (state.currentIndex >= state.questions.size) {
            finishQuiz()
            return
        }

        val question = state.questions[state.currentIndex]
        audioEngine.playMusicSnippet(
            riffId = question.snippetRiffId,
            title = "${question.title} - ${question.artist}",
            durationSec = 15
        )

        _quizState.update {
            it.copy(
                timeRemainingSeconds = 15,
                selectedOptionIndex = -1,
                answerState = AnswerState.NONE,
                funFactRevealed = ""
            )
        }

        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            for (sec in 15 downTo 1) {
                _quizState.update { it.copy(timeRemainingSeconds = sec) }
                if (sec <= 5) {
                    audioEngine.playTickSound()
                }
                delay(1000)
            }
            if (_quizState.value.answerState == AnswerState.NONE) {
                onOptionSelected(-1)
            }
        }
    }

    fun onOptionSelected(index: Int) {
        if (_quizState.value.answerState != AnswerState.NONE) return
        timerJob?.cancel()
        val currentQ = _quizState.value.questions[_quizState.value.currentIndex]

        val isCorrect = index == currentQ.correctIndex
        val timeLeft = _quizState.value.timeRemainingSeconds

        if (isCorrect) {
            val speedBonus = timeLeft * 10
            val streakBonus = _quizState.value.streak * 20
            val pointsEarned = 100 + speedBonus + streakBonus
            audioEngine.playCorrectSound()
            _quizState.update {
                it.copy(
                    selectedOptionIndex = index,
                    answerState = AnswerState.CORRECT,
                    score = it.score + pointsEarned,
                    streak = it.streak + 1,
                    funFactRevealed = currentQ.funFact
                )
            }
        } else {
            audioEngine.playWrongSound()
            _quizState.update {
                it.copy(
                    selectedOptionIndex = index,
                    answerState = if (index == -1) AnswerState.TIMEOUT else AnswerState.WRONG,
                    streak = 0,
                    funFactRevealed = currentQ.funFact
                )
            }
        }
    }

    fun nextQuestion() {
        val nextIdx = _quizState.value.currentIndex + 1
        if (nextIdx >= _quizState.value.questions.size) {
            finishQuiz()
        } else {
            _quizState.update { it.copy(currentIndex = nextIdx) }
            playCurrentQuestion()
        }
    }

    private fun finishQuiz() {
        timerJob?.cancel()
        audioEngine.stop()
        audioEngine.playCoinSound()
        val finalScore = _quizState.value.score
        val isWin = finalScore >= 300

        localRepository.recordGameResult(finalScore, isWin)
        _userProfile.value = localRepository.userProfile.value

        // Sync to Cloud Firestore if signed in
        if (_isAuthenticated.value && firestoreRepository != null) {
            viewModelScope.launch {
                try {
                    firestoreRepository.recordGameResult(finalScore, isWin, _userProfile.value)
                } catch (e: Exception) {
                    Log.e("ViewModel", "Failed to sync game result to Firestore: ${e.message}")
                }
            }
        }

        _quizState.update { it.copy(isFinished = true) }
    }

    // --- 1v1 Battle System ---

    fun startBattle(opponentName: String = "LagosGrooveMaster", country: String = "🇳🇬 Nigeria") {
        val questions = SampleMusicDatabase.questions.shuffled().take(5)
        _battleState.value = BattleState(
            round = 1,
            totalRounds = 5,
            playerScore = 0,
            opponentScore = 0,
            opponentName = opponentName,
            opponentCountry = country,
            status = BattleStatus.IN_PROGRESS,
            currentQuestion = questions.firstOrNull()
        )
        navigateTo(Screen.BATTLE)
        playBattleRound(1, questions)
    }

    private fun playBattleRound(round: Int, questions: List<SongQuestion>) {
        val question = questions.getOrNull(round - 1) ?: return
        audioEngine.playMusicSnippet(
            riffId = question.snippetRiffId,
            title = "Battle Round $round: ${question.title}",
            durationSec = 10
        )
        _battleState.update {
            it.copy(
                round = round,
                status = BattleStatus.IN_PROGRESS,
                currentQuestion = question
            )
        }
    }

    fun answerBattleQuestion(chosenIndex: Int) {
        val q = _battleState.value.currentQuestion ?: return
        val isPlayerCorrect = chosenIndex == q.correctIndex

        val opponentCorrect = Random.nextFloat() > 0.3f
        val playerPoints = if (isPlayerCorrect) Random.nextInt(85, 120) else 0
        val opponentPoints = if (opponentCorrect) Random.nextInt(75, 110) else 0

        if (isPlayerCorrect) audioEngine.playCorrectSound() else audioEngine.playWrongSound()

        val newPlayerScore = _battleState.value.playerScore + playerPoints
        val newOpponentScore = _battleState.value.opponentScore + opponentPoints

        val nextRound = _battleState.value.round + 1
        if (nextRound > _battleState.value.totalRounds) {
            val won = newPlayerScore >= newOpponentScore
            localRepository.recordGameResult(newPlayerScore, won)
            _userProfile.value = localRepository.userProfile.value
            audioEngine.playCoinSound()
            _battleState.update {
                it.copy(
                    playerScore = newPlayerScore,
                    opponentScore = newOpponentScore,
                    status = BattleStatus.MATCH_COMPLETE
                )
            }
            if (_isAuthenticated.value && firestoreRepository != null) {
                viewModelScope.launch {
                    try {
                        firestoreRepository.recordGameResult(newPlayerScore, won, _userProfile.value)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } else {
            _battleState.update {
                it.copy(
                    playerScore = newPlayerScore,
                    opponentScore = newOpponentScore,
                    round = nextRound
                )
            }
            playBattleRound(nextRound, SampleMusicDatabase.questions)
        }
    }

    fun generateNewFriendCode() {
        _friendInviteCode.value = "MUSIZ-BATTLE-${Random.nextInt(1000, 9999)}"
        showToast("New match code generated: ${_friendInviteCode.value}")
    }

    // --- Leaderboards ---

    fun selectLeaderboardTab(tab: String) {
        _selectedLeaderboardTab.value = tab
    }

    fun getFilteredLeaderboards(): List<LeaderboardEntry> {
        val cloudList = _cloudLeaderboards.value
        return when (_selectedLeaderboardTab.value) {
            "Global" -> if (cloudList.isNotEmpty()) cloudList else LeaderboardData.globalRankings
            "Nigeria" -> LeaderboardData.nigeriaRankings
            "South Africa" -> LeaderboardData.southAfricaRankings
            "UK" -> LeaderboardData.ukRankings
            "USA" -> LeaderboardData.usaRankings
            else -> if (cloudList.isNotEmpty()) cloudList else LeaderboardData.globalRankings
        }
    }

    // --- Emerging Artist & Rewards ---

    fun playArtistTrack(artist: EmergingArtist) {
        audioEngine.playMusicSnippet(
            riffId = artist.snippetRiffId,
            title = "${artist.featuredTrackTitle} by ${artist.stageName}",
            durationSec = 15
        )
        showToast("Now Playing: ${artist.featuredTrackTitle}")
    }

    fun submitArtistSong(
        artistName: String,
        title: String,
        genre: String,
        year: String,
        link: String,
        rightsConfirmed: Boolean
    ): Boolean {
        val success = localRepository.submitArtistTrack(artistName, title, genre, year, link, rightsConfirmed)
        _submissions.value = localRepository.submissions.value

        if (success && _isAuthenticated.value && firestoreRepository != null) {
            viewModelScope.launch {
                try {
                    firestoreRepository.submitArtistSong(artistName, title, genre, year, link, rightsConfirmed)
                } catch (e: Exception) {
                    Log.e("ViewModel", "Failed to submit song to Firestore: ${e.message}")
                }
            }
        }

        if (success) {
            showToast("Song submitted for review! Rights verified.")
        } else {
            showToast("Please confirm rights ownership to proceed.")
        }
        return success
    }

    fun claimCampaignReward(campaign: RewardCampaign) {
        val userScore = userProfile.value.xp
        if (userScore < campaign.minScoreToQualify) {
            showToast("Requires ${campaign.minScoreToQualify} XP to qualify. Keep playing!")
            return
        }
        if (claimedRewardIds.value.contains(campaign.id)) {
            showToast("Reward already claimed this cycle!")
            return
        }

        localRepository.claimReward(campaign.id)
        audioEngine.playCoinSound()
        showToast("Qualified! Claim token sent to verification queue.")
    }

    fun setCountry(countryName: String, flag: String) {
        localRepository.updateCountry(countryName, flag)
        _userProfile.value = localRepository.userProfile.value
        showToast("Country updated to $countryName $flag")
    }

    fun toggleFanClubFollow(clubId: String) {
        _fanClubs.update { clubs ->
            clubs.map { club ->
                if (club.id == clubId) {
                    val newFollowing = !club.isFollowing
                    club.copy(
                        isFollowing = newFollowing,
                        followersCount = if (newFollowing) club.followersCount + 1 else club.followersCount - 1
                    )
                } else club
            }
        }
        val isNowFollowing = _fanClubs.value.find { it.id == clubId }?.isFollowing == true
        showToast(if (isNowFollowing) "Joined Fan Club! Exclusive drops enabled." else "Left Fan Club.")
    }

    fun likeFanClubPost(postId: String) {
        _fanClubs.update { clubs ->
            clubs.map { club ->
                club.copy(
                    posts = club.posts.map { post ->
                        if (post.id == postId) {
                            val newLiked = !post.isLiked
                            post.copy(
                                isLiked = newLiked,
                                likesCount = if (newLiked) post.likesCount + 1 else post.likesCount - 1
                            )
                        } else post
                    }
                )
            }
        }
    }

    fun sendChatMessage(text: String) {
        val newMsg = DirectChatMessage(
            id = "msg-${System.currentTimeMillis()}",
            senderName = userProfile.value.username,
            messageText = text,
            timestamp = "Just now",
            isFromMe = true
        )
        _communityChatMessages.update { it + newMsg }
    }

    fun reportPost(postId: String, reason: String) {
        val newRep = AdminReport(
            id = "rep-${System.currentTimeMillis()}",
            reportCategory = "Post Moderation",
            reportedTarget = "Post ID $postId",
            reporterName = userProfile.value.username,
            reasonDetail = reason,
            status = "PENDING_REVIEW",
            date = "Today"
        )
        _adminReports.update { it + newRep }
        showToast("Report submitted to moderation queue.")
    }

    fun donateToCrowdfunding(campaignId: String, amountUsd: Double) {
        _crowdfundingCampaigns.update { campaigns ->
            campaigns.map { c ->
                if (c.id == campaignId) {
                    c.copy(
                        raisedAmountUsd = c.raisedAmountUsd + amountUsd,
                        supportersCount = c.supportersCount + 1
                    )
                } else c
            }
        }
        audioEngine.playCoinSound()
        showToast("Contributed \$${amountUsd.toInt()}! Donor perk unlocked.")
    }

    fun createCrowdfundingCampaign(title: String, category: String, story: String, targetAmountUsd: Double) {
        val newCampaign = CrowdfundingCampaign(
            id = "cf-${System.currentTimeMillis()}",
            artistName = userProfile.value.username,
            artistCountry = userProfile.value.country,
            artistFlag = userProfile.value.countryFlag,
            title = title,
            purposeCategory = category,
            story = story,
            targetAmountUsd = targetAmountUsd,
            raisedAmountUsd = 0.0,
            supportersCount = 0,
            daysRemaining = 30,
            verifiedCreator = true,
            perksDescription = "Exclusive early stems and personalized shoutouts for top supporters."
        )
        _crowdfundingCampaigns.update { listOf(newCampaign) + it }
        showToast("Campaign submitted for rights & identity verification!")
    }

    fun submitBookingRequest(request: BookingRequest) {
        _bookingRequests.update { listOf(request) + it }
        showToast("Quotation request sent to talent manager!")
    }

    fun resolveAdminReport(reportId: String) {
        _adminReports.update { reports ->
            reports.map { if (it.id == reportId) it.copy(status = "RESOLVED") else it }
        }
        showToast("Report marked as resolved.")
    }

    fun approveArtistSubmission(submissionId: String) {
        _submissions.update { subs ->
            subs.map { if (it.id == submissionId) it.copy(status = SubmissionStatus.APPROVED_LIVE) else it }
        }
        showToast("Submission approved and released to Discover feed!")
    }

    fun rejectArtistSubmission(submissionId: String) {
        _submissions.update { subs ->
            subs.map { if (it.id == submissionId) it.copy(status = SubmissionStatus.REJECTED) else it }
        }
        showToast("Submission rejected.")
    }

    // ==========================================
    // BUSINESS HUB ACTIONS
    // ==========================================
    fun registerBusiness(req: RegisterBusinessRequest) {
        val newBusiness = BusinessListing(
            id = "biz-${System.currentTimeMillis()}",
            name = req.name,
            tagline = req.description.take(60),
            category = req.category,
            description = req.description,
            location = req.location,
            rating = 5.0,
            reviewsCount = 1,
            contactPhone = req.phone,
            whatsapp = req.whatsapp,
            website = "https://musizearn.com/biz/${req.name.lowercase().replace(" ", "-")}",
            email = req.email,
            isSponsored = false,
            isVerified = false,
            viewsCount = 1,
            enquiriesCount = 0,
            specialOffer = req.specialOffer.ifBlank { null }
        )
        _businesses.update { listOf(newBusiness) + it }
        showToast("Business registered! Verification team will review within 24 hours.")
    }

    fun submitBusinessEnquiry(
        businessId: String,
        businessName: String,
        clientName: String,
        clientPhone: String,
        clientEmail: String,
        message: String
    ) {
        val enquiry = BusinessEnquiry(
            id = "enq-${System.currentTimeMillis()}",
            businessId = businessId,
            businessName = businessName,
            clientName = clientName,
            clientPhone = clientPhone,
            clientEmail = clientEmail,
            message = message,
            dateSubmitted = "Today, Just now"
        )
        _businessEnquiries.update { listOf(enquiry) + it }
        _businesses.update { list ->
            list.map { if (it.id == businessId) it.copy(enquiriesCount = it.enquiriesCount + 1) else it }
        }
        showToast("Enquiry dispatched to $businessName via direct business relay!")
    }

    // ==========================================
    // PRODUCER ZONE ACTIONS
    // ==========================================
    fun previewBeat(beat: ProducerBeat) {
        if (_currentlyPlayingBeatId.value == beat.id) {
            audioEngine.stop()
            _currentlyPlayingBeatId.value = null
        } else {
            audioEngine.stop()
            _currentlyPlayingBeatId.value = beat.id
            audioEngine.playRiff(beat.bpm, beat.genre)
            _beats.update { list ->
                list.map { if (it.id == beat.id) it.copy(playCount = it.playCount + 1) else it }
            }
        }
    }

    fun stopBeatPreview() {
        audioEngine.stop()
        _currentlyPlayingBeatId.value = null
    }

    fun uploadBeat(
        title: String,
        producer: String,
        genre: String,
        bpm: Int,
        musicalKey: String,
        mood: String,
        nonExclPrice: Double,
        exclPrice: Double,
        tags: List<String>
    ) {
        val newBeat = ProducerBeat(
            id = "beat-${System.currentTimeMillis()}",
            title = title,
            producerName = producer.ifBlank { userProfile.value.username },
            genre = genre,
            bpm = bpm,
            musicalKey = musicalKey,
            mood = mood,
            playCount = 1,
            nonExclusivePriceUsd = nonExclPrice,
            exclusivePriceUsd = exclPrice,
            isExclusiveSold = false,
            tags = tags
        )
        _beats.update { listOf(newBeat) + it }
        showToast("Beat '$title' uploaded to Producer Zone with legal protections!")
    }

    fun purchaseBeatLicense(
        beatId: String,
        licenseType: BeatLicenseType,
        buyerName: String,
        buyerEmail: String
    ) {
        val beat = _beats.value.firstOrNull { it.id == beatId } ?: return
        if (licenseType == BeatLicenseType.EXCLUSIVE_BUYOUT && beat.isExclusiveSold) {
            showToast("Error: Exclusive rights have already been acquired for this master.")
            return
        }

        val amount = when (licenseType) {
            BeatLicenseType.FREE_AUDITION -> 0.0
            BeatLicenseType.NON_EXCLUSIVE_LEASE -> beat.nonExclusivePriceUsd
            BeatLicenseType.EXCLUSIVE_BUYOUT -> beat.exclusivePriceUsd
        }

        val certCode = "MUSIZ-LIC-${Random.nextInt(100000, 999999)}"
        val tx = BeatLicenseTransaction(
            id = "tx-${System.currentTimeMillis()}",
            beatId = beatId,
            beatTitle = beat.title,
            producerName = beat.producerName,
            licenseType = licenseType,
            amountPaidUsd = amount,
            buyerName = buyerName,
            buyerEmail = buyerEmail,
            timestamp = "Just now",
            licenseCertificateCode = certCode
        )

        _beatTransactions.update { listOf(tx) + it }
        if (licenseType == BeatLicenseType.EXCLUSIVE_BUYOUT) {
            _beats.update { list ->
                list.map { if (it.id == beatId) it.copy(isExclusiveSold = true, buyerName = buyerName) else it }
            }
        }

        audioEngine.playCoinSound()
        showToast("License secured! Certificate code: $certCode sent to $buyerEmail")
    }

    // ==========================================
    // MUSIZEARN ACADEMY ACTIONS
    // ==========================================
    fun completeLessonQuiz(courseId: String, lessonId: String, selectedIndex: Int): Boolean {
        val course = _academyCourses.value.firstOrNull { it.id == courseId } ?: return false
        val lesson = course.lessons.firstOrNull { it.id == lessonId } ?: return false
        val isCorrect = selectedIndex == lesson.quizQuestion.correctIndex

        if (isCorrect) {
            _completedLessons.update { it + lessonId }
            audioEngine.playCorrectSound()
            userProfile.value.let { prof ->
                _userProfile.value = prof.copy(points = prof.points + 100)
            }
            showToast("Correct! +100 XP gained. Module passed.")
        } else {
            audioEngine.playWrongSound()
            showToast("Incorrect answer. Read key takeaways and try again!")
        }
        return isCorrect
    }

    fun claimCourseCertificate(courseId: String) {
        val course = _academyCourses.value.firstOrNull { it.id == courseId } ?: return
        val existing = _academyCertificates.value.firstOrNull { it.courseId == courseId }
        if (existing != null) {
            showToast("Certificate already claimed! Number: ${existing.certificateNumber}")
            return
        }

        val certNumber = "CERT-SMR-${Random.nextInt(10000, 99999)}"
        val certificate = AcademyCertificate(
            id = "cert-${System.currentTimeMillis()}",
            courseId = courseId,
            courseTitle = course.title,
            studentName = userProfile.value.username,
            issueDate = "Today",
            certificateNumber = certNumber,
            instructorSignature = "Afolabi Oluwadamilare Simeon — SimeonJr Studios"
        )
        _academyCertificates.update { listOf(certificate) + it }
        audioEngine.playCoinSound()
        showToast("Graduation Certificate $certNumber Issued & Recorded!")
    }

    // ==========================================
    // SOUND LIBRARY ACTIONS
    // ==========================================
    fun previewSoundAsset(asset: SoundAsset) {
        if (_currentlyPlayingSoundId.value == asset.id) {
            audioEngine.stop()
            _currentlyPlayingSoundId.value = null
        } else {
            audioEngine.stop()
            _currentlyPlayingSoundId.value = asset.id
            audioEngine.playRiff(asset.bpm ?: 110, asset.category.name)
            _soundAssets.update { list ->
                list.map { if (it.id == asset.id) it.copy(downloadCount = it.downloadCount + 1) else it }
            }
        }
    }

    fun stopSoundPreview() {
        audioEngine.stop()
        _currentlyPlayingSoundId.value = null
    }

    // ==========================================
    // CREATOR SERVICES ACTIONS
    // ==========================================
    fun submitServiceQuote(quote: ServiceQuoteRequest) {
        _serviceQuotes.update { listOf(quote) + it }
        audioEngine.playCoinSound()
        showToast("Quote inquiry delivered to ${quote.providerName}!")
    }
}
