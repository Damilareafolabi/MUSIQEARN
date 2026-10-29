package com.example.model

enum class BookingStatus(val label: String) {
    SUBMITTED("Submitted"),
    IN_REVIEW("Manager In Review"),
    QUOTATION_ISSUED("Quotation Issued"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled")
}

data class FanClubPost(
    val id: String,
    val celebrityId: String,
    val celebrityName: String,
    val celebrityAvatar: String,
    val isVerified: Boolean = true,
    val content: String,
    val timestamp: String,
    val likesCount: Int,
    val isLiked: Boolean = false,
    val commentsCount: Int = 12,
    val sponsoredRewardBadge: String? = null
)

data class CelebrityFanClub(
    val id: String,
    val celebrityName: String,
    val handle: String,
    val roleTitle: String,
    val bio: String,
    val country: String,
    val flag: String,
    val isVerified: Boolean = true,
    val followersCount: Int,
    val isFollowing: Boolean = false,
    val posts: List<FanClubPost> = emptyList(),
    val totalGiveawaysHosted: Int = 8
)

data class CrowdfundingCampaign(
    val id: String,
    val artistName: String,
    val artistCountry: String,
    val artistFlag: String,
    val title: String,
    val purposeCategory: String, // e.g. "Recording & Production", "Music Video", "Tour & Performance", "Studio Equipment"
    val story: String,
    val targetAmountUsd: Double,
    val raisedAmountUsd: Double,
    val supportersCount: Int,
    val daysRemaining: Int,
    val verifiedCreator: Boolean = true,
    val perksDescription: String,
    val updates: List<String> = emptyList()
)

data class CelebrityBookingProfile(
    val id: String,
    val celebrityName: String,
    val specialty: String,
    val country: String,
    val flag: String,
    val isVerified: Boolean = true,
    val managerContact: String,
    val startingFeeEstimate: String,
    val rating: Float = 4.9f,
    val completedBookings: Int = 34,
    val acceptedEventTypes: List<String> = listOf("Concert Headline", "Club Hosting", "Corporate Gala", "Festival Appearance")
)

data class BookingRequest(
    val id: String,
    val celebrityName: String,
    val organizerName: String,
    val organizerEmail: String,
    val eventType: String,
    val eventDate: String,
    val venueCity: String,
    val expectedAudience: String,
    val budgetOffer: String,
    val specialRequirements: String,
    val status: BookingStatus = BookingStatus.SUBMITTED,
    val dateSubmitted: String
)

data class DirectChatMessage(
    val id: String,
    val senderName: String,
    val messageText: String,
    val timestamp: String,
    val isFromMe: Boolean
)

data class AdminReport(
    val id: String,
    val reportCategory: String, // e.g. "Music Rights Dispute", "Spam", "Impersonation", "Disputed Campaign"
    val reportedTarget: String,
    val reporterName: String,
    val reasonDetail: String,
    val status: String = "PENDING_REVIEW",
    val date: String
)
