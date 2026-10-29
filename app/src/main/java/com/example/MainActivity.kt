package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MusizEarnViewModel
import com.example.ui.Screen
import com.example.ui.components.FloatingMiniPlayer
import com.example.ui.components.MusizEarnBottomNav
import com.example.ui.components.MusizEarnTopBar
import com.example.ui.screens.AdminPortalScreen
import com.example.ui.screens.ArtistPortalScreen
import com.example.ui.screens.BattleScreen
import com.example.ui.screens.CelebrityBookingsScreen
import com.example.ui.screens.CrowdfundingScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.FanClubsScreen
import com.example.ui.screens.FullPlayerScreen
import com.example.ui.screens.GameplayScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RewardsScreen
import com.example.ui.theme.AcidLime
import com.example.ui.theme.CardOlive
import com.example.ui.theme.CardOliveBorder
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.TextPrimary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MusizEarnApp()
            }
        }
    }
}

@Composable
fun MusizEarnApp(
    viewModel: MusizEarnViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val quizState by viewModel.quizState.collectAsState()
    val battleState by viewModel.battleState.collectAsState()
    val friendCode by viewModel.friendInviteCode.collectAsState()
    val submissions by viewModel.submissions.collectAsState()
    val claimedRewardIds by viewModel.claimedRewardIds.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val isPlayingAudio by viewModel.audioEngine.isPlaying.collectAsState()
    val audioProgress by viewModel.audioEngine.progress.collectAsState()
    val selectedLeaderboardTab by viewModel.selectedLeaderboardTab.collectAsState()
    val currentTrack by viewModel.currentTrack.collectAsState()
    val isFullPlayerExpanded by viewModel.isFullPlayerExpanded.collectAsState()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    val fanClubs by viewModel.fanClubs.collectAsState()
    val crowdfundingCampaigns by viewModel.crowdfundingCampaigns.collectAsState()
    val celebrityBookings by viewModel.celebrityBookings.collectAsState()
    val bookingRequests by viewModel.bookingRequests.collectAsState()
    val communityChatMessages by viewModel.communityChatMessages.collectAsState()
    val adminReports by viewModel.adminReports.collectAsState()

    // Back handling for custom navigation and player collapse
    BackHandler(enabled = isFullPlayerExpanded || currentScreen != Screen.HOME) {
        if (isFullPlayerExpanded) {
            viewModel.setFullPlayerExpanded(false)
        } else {
            viewModel.navigateTo(Screen.HOME)
        }
    }

    val isFullScreenSection = currentScreen == Screen.GAMEPLAY ||
            currentScreen == Screen.BATTLE ||
            currentScreen == Screen.ARTIST_PORTAL ||
            currentScreen == Screen.FAN_CLUBS ||
            currentScreen == Screen.CROWDFUNDING ||
            currentScreen == Screen.BOOKINGS ||
            currentScreen == Screen.ADMIN_PORTAL

    val showTopBar = !isFullPlayerExpanded && !isFullScreenSection
    val showBottomControls = !isFullPlayerExpanded && !isFullScreenSection

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBlack),
        topBar = {
            if (showTopBar) {
                MusizEarnTopBar(
                    userProfile = userProfile,
                    isMuted = viewModel.audioEngine.isMuted,
                    onToggleMute = { viewModel.toggleAudioMute() },
                    onProfileClick = { viewModel.navigateTo(Screen.PROFILE) }
                )
            }
        },
        bottomBar = {
            if (showBottomControls) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Floating Mini-Player bar (Boomplay / Spotify style)
                    FloatingMiniPlayer(
                        currentTrack = currentTrack,
                        isPlaying = isPlayingAudio,
                        progress = audioProgress,
                        onTogglePlay = { viewModel.togglePlayback() },
                        onExpand = { viewModel.setFullPlayerExpanded(true) }
                    )
                    // Floating Curved Bottom Navigation Dock
                    MusizEarnBottomNav(
                        currentScreen = currentScreen,
                        onNavigate = { screen -> viewModel.navigateTo(screen) }
                    )
                }
            }
        },
        containerColor = ObsidianBlack
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                Screen.HOME -> HomeScreen(
                    userProfile = userProfile,
                    friendInviteCode = friendCode,
                    onStartGame = { mode -> viewModel.startQuiz(mode) },
                    onStartBattle = { viewModel.startBattle() },
                    onPlayTrack = { track -> viewModel.playTrack(track) },
                    onNavigate = { screen -> viewModel.navigateTo(screen) }
                )
                Screen.GAMEPLAY -> GameplayScreen(
                    quizState = quizState,
                    audioEngine = viewModel.audioEngine,
                    isPlayingAudio = isPlayingAudio,
                    onOptionSelected = { idx -> viewModel.onOptionSelected(idx) },
                    onNextQuestion = { viewModel.nextQuestion() },
                    onPlayAgain = { viewModel.startQuiz(quizState.mode) },
                    onExit = { viewModel.navigateTo(Screen.HOME) }
                )
                Screen.BATTLE -> BattleScreen(
                    battleState = battleState,
                    userProfile = userProfile,
                    friendCode = friendCode,
                    isPlayingAudio = isPlayingAudio,
                    onAnswerOption = { optIdx -> viewModel.answerBattleQuestion(optIdx) },
                    onGenerateCode = { viewModel.generateNewFriendCode() },
                    onStartBattle = { oppName, oppCountry -> viewModel.startBattle(oppName, oppCountry) },
                    onExit = { viewModel.navigateTo(Screen.HOME) }
                )
                Screen.LEADERBOARDS -> LeaderboardScreen(
                    entries = viewModel.getFilteredLeaderboards(),
                    selectedTab = selectedLeaderboardTab,
                    onSelectTab = { tab -> viewModel.selectLeaderboardTab(tab) },
                    userProfile = userProfile
                )
                Screen.DISCOVER -> DiscoverScreen(
                    onPlayTrack = { artist -> viewModel.playArtistTrack(artist) },
                    onStartGame = { mode -> viewModel.startQuiz(mode) },
                    onNavigate = { screen -> viewModel.navigateTo(screen) }
                )
                Screen.REWARDS -> RewardsScreen(
                    userProfile = userProfile,
                    claimedRewardIds = claimedRewardIds,
                    onClaimReward = { campaign -> viewModel.claimCampaignReward(campaign) }
                )
                Screen.ARTIST_PORTAL -> ArtistPortalScreen(
                    submissions = submissions,
                    onSubmitTrack = { artist, title, genre, year, link, rights ->
                        viewModel.submitArtistSong(artist, title, genre, year, link, rights)
                    },
                    onExit = { viewModel.navigateTo(Screen.HOME) }
                )
                Screen.PROFILE -> ProfileScreen(
                    userProfile = userProfile,
                    isMuted = viewModel.audioEngine.isMuted,
                    isAuthenticated = isAuthenticated,
                    onToggleMute = { viewModel.toggleAudioMute() },
                    onNavigate = { screen -> viewModel.navigateTo(screen) },
                    onSetCountry = { country, flag -> viewModel.setCountry(country, flag) }
                )
                Screen.FAN_CLUBS -> FanClubsScreen(
                    fanClubs = fanClubs,
                    chatMessages = communityChatMessages,
                    onToggleFollow = { clubId -> viewModel.toggleFanClubFollow(clubId) },
                    onLikePost = { postId -> viewModel.likeFanClubPost(postId) },
                    onSendMessage = { text -> viewModel.sendChatMessage(text) },
                    onReportPost = { postId, reason -> viewModel.reportPost(postId, reason) },
                    onNavigate = { screen -> viewModel.navigateTo(screen) },
                    onExit = { viewModel.navigateTo(Screen.HOME) }
                )
                Screen.CROWDFUNDING -> CrowdfundingScreen(
                    campaigns = crowdfundingCampaigns,
                    onDonate = { campaignId, amount -> viewModel.donateToCrowdfunding(campaignId, amount) },
                    onCreateCampaign = { title, category, story, target ->
                        viewModel.createCrowdfundingCampaign(title, category, story, target)
                    },
                    onExit = { viewModel.navigateTo(Screen.HOME) }
                )
                Screen.BOOKINGS -> CelebrityBookingsScreen(
                    profiles = celebrityBookings,
                    requests = bookingRequests,
                    onSubmitBookingRequest = { req -> viewModel.submitBookingRequest(req) },
                    onExit = { viewModel.navigateTo(Screen.HOME) }
                )
                Screen.ADMIN_PORTAL -> AdminPortalScreen(
                    reports = adminReports,
                    submissions = submissions,
                    onResolveReport = { repId -> viewModel.resolveAdminReport(repId) },
                    onApproveSubmission = { subId -> viewModel.approveArtistSubmission(subId) },
                    onRejectSubmission = { subId -> viewModel.rejectArtistSubmission(subId) },
                    onExit = { viewModel.navigateTo(Screen.HOME) }
                )
            }

            // Full-Screen DJ Jog-Wheel Player (Screen 3 Reference Design)
            AnimatedVisibility(
                visible = isFullPlayerExpanded && currentTrack != null,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                currentTrack?.let { track ->
                    FullPlayerScreen(
                        currentTrack = track,
                        isPlaying = isPlayingAudio,
                        progress = audioProgress,
                        onTogglePlay = { viewModel.togglePlayback() },
                        onNext = { viewModel.nextTrack() },
                        onPrev = { viewModel.prevTrack() },
                        onStartQuizForTrack = { selected -> viewModel.startQuizForTrack(selected) },
                        onExit = { viewModel.setFullPlayerExpanded(false) }
                    )
                }
            }

            // Notification / Toast Message Overlay
            AnimatedVisibility(
                visible = toastMessage != null && !isFullPlayerExpanded,
                enter = fadeIn() + slideInVertically { -it },
                exit = fadeOut() + slideOutVertically { -it },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                toastMessage?.let { msg ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(CardOlive)
                            .border(1.dp, AcidLime, RoundedCornerShape(14.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .testTag("app_toast_message")
                    ) {
                        Text(
                            text = msg,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
