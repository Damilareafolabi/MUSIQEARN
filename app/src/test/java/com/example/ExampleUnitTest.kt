package com.example

import com.example.data.RewardsData
import com.example.data.SampleMusicDatabase
import com.example.data.UserDataRepository
import com.example.model.GameMode
import com.example.model.SubmissionStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testMusicDatabaseQuestionsIntegrity() {
        val questions = SampleMusicDatabase.questions
        assertTrue("Questions catalog should not be empty", questions.isNotEmpty())

        for (q in questions) {
            assertEquals("Each question must provide exactly 4 options", 4, q.options.size)
            assertTrue("Correct index must be within range 0..3", q.correctIndex in 0..3)
            assertNotNull("Artist must not be null", q.artist)
            assertNotNull("Snippet riff ID must be provided", q.snippetRiffId)
        }
    }

    @Test
    fun testNameTheArtistModeTransformation() {
        val artistQuestions = SampleMusicDatabase.getQuestionsForMode(GameMode.NAME_ARTIST, 5)
        assertEquals(5, artistQuestions.size)

        for (q in artistQuestions) {
            assertEquals(4, q.options.size)
            assertEquals("The correct option must match the actual artist name", q.artist, q.options[q.correctIndex])
        }
    }

    @Test
    fun testUserDataRepositoryScoringAndStreaks() {
        val repo = UserDataRepository()
        val initialProfile = repo.userProfile.value
        val initialGames = initialProfile.totalGamesPlayed

        val initialWins = initialProfile.totalWins

        // Record a win with 350 points
        repo.recordGameResult(pointsEarned = 350, won = true)
        val afterWin = repo.userProfile.value
        assertEquals(initialGames + 1, afterWin.totalGamesPlayed)
        assertEquals(initialWins + 1, afterWin.totalWins)
        assertTrue("XP should increase by earned points", afterWin.xp > initialProfile.xp)
        assertEquals(initialProfile.currentStreak + 1, afterWin.currentStreak)

        // Record a loss
        repo.recordGameResult(pointsEarned = 100, won = false)
        val afterLoss = repo.userProfile.value
        assertEquals(initialGames + 2, afterLoss.totalGamesPlayed)
        assertEquals(initialWins + 1, afterLoss.totalWins)
        assertEquals(0, afterLoss.currentStreak)
    }

    @Test
    fun testArtistSubmissionRightsEnforcement() {
        val repo = UserDataRepository()

        // Attempt submission without rights confirmation should fail
        val failed = repo.submitArtistTrack(
            artistName = "Unauthorized Uploader",
            songTitle = "Bootleg Track",
            genre = "Afrobeats",
            releaseYear = "2024",
            streamingLink = "https://example.com",
            rightsConfirmed = false
        )
        assertFalse("Submission must be rejected if rights are not confirmed", failed)

        // Submission with rights confirmed should succeed
        val success = repo.submitArtistTrack(
            artistName = "Tayo Vibe",
            songTitle = "Lagos Sunrise",
            genre = "Afro-Fusion",
            releaseYear = "2024",
            streamingLink = "https://audiomack.com/tayo",
            rightsConfirmed = true
        )
        assertTrue(success)
        val latest = repo.submissions.value.first()
        assertEquals("Tayo Vibe", latest.artistName)
        assertEquals(SubmissionStatus.PENDING_REVIEW, latest.status)
    }

    @Test
    fun testRewardsDataIntegrity() {
        val campaigns = RewardsData.campaigns
        assertTrue("Active campaigns must exist", campaigns.isNotEmpty())
        for (camp in campaigns) {
            assertTrue("Min score to qualify must be positive", camp.minScoreToQualify > 0)
            assertTrue("Remaining days must be positive", camp.daysRemaining > 0)
            assertNotNull("Sponsor disclaimer must be present", camp.sponsorDisclaimer)
        }
    }

    @Test
    fun testPlatformEcosystemDataIntegrity() {
        // 1. Fan Clubs
        val clubs = com.example.data.PlatformData.fanClubs
        assertTrue("Celebrity fan clubs must not be empty", clubs.isNotEmpty())
        for (club in clubs) {
            assertTrue("Celebrity name must not be blank", club.celebrityName.isNotBlank())
            assertTrue("Followers count must be positive", club.followersCount > 0)
            assertTrue("Must contain announcements or posts", club.posts.isNotEmpty())
        }

        // 2. Crowdfunding
        val campaigns = com.example.data.PlatformData.crowdfundingCampaigns
        assertTrue("Crowdfunding campaigns must exist", campaigns.isNotEmpty())
        for (c in campaigns) {
            assertTrue("Target amount must be positive", c.targetAmountUsd > 0)
            assertTrue("Raised amount must be non-negative", c.raisedAmountUsd >= 0)
            assertTrue("Remaining days must be positive", c.daysRemaining > 0)
        }

        // 3. Celebrity Bookings
        val bookings = com.example.data.PlatformData.celebrityBookings
        assertTrue("Celebrity booking profiles must exist", bookings.isNotEmpty())
        for (b in bookings) {
            assertTrue("Manager contact must be declared", b.managerContact.isNotBlank())
            assertTrue("Rating must be above 4.0", b.rating >= 4.0f)
            assertTrue("Completed bookings must be non-negative", b.completedBookings >= 0)
        }

        // 4. Admin Reports
        val reports = com.example.data.PlatformData.adminReports
        assertTrue("Admin moderation reports must exist", reports.isNotEmpty())
        for (rep in reports) {
            assertTrue("Report category must be set", rep.reportCategory.isNotBlank())
            assertTrue("Reported target must be set", rep.reportedTarget.isNotBlank())
        }
    }
}
