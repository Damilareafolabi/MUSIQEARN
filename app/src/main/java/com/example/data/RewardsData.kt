package com.example.data

import com.example.model.RewardCampaign
import com.example.model.RewardType

object RewardsData {

    val campaigns: List<RewardCampaign> = listOf(
        RewardCampaign(
            id = "rew_mtn_airtime",
            title = "MTN Afrobeats Airtime Sprint",
            sponsorName = "SimeonJr Studios & Partner Telco",
            rewardType = RewardType.AIRTIME,
            prizeValue = "₦2,000 Airtime",
            totalPool = "₦200,000",
            remainingBudget = "₦142,000",
            country = "🇳🇬 Nigeria",
            minScoreToQualify = 1200,
            daysRemaining = 4,
            isLive = true,
            sponsorDisclaimer = "100% Sponsor-funded airtime vouchers delivered via automated pin/direct topup.",
            verificationNotice = "Requires valid phone number and server-validated match score. Limit 1 claim per player/week."
        ),
        RewardCampaign(
            id = "rew_airtel_data",
            title = "Airtel 5GB Stream & Jam Challenge",
            sponsorName = "SoundHub Promotions",
            rewardType = RewardType.DATA_BUNDLE,
            prizeValue = "5GB High-Speed Data",
            totalPool = "500 GB",
            remainingBudget = "315 GB",
            country = "🇳🇬 Nigeria & 🇬🇭 Ghana",
            minScoreToQualify = 1500,
            daysRemaining = 8,
            isLive = true,
            sponsorDisclaimer = "Direct mobile data bundle credited within 24 hours of admin qualification review.",
            verificationNotice = "Cheating, speedbots, or emulator tampering immediately forfeits qualification."
        ),
        RewardCampaign(
            id = "rew_studio_launch_cash",
            title = "SimeonJr Studio Founder Cup",
            sponsorName = "Afolabi Oluwadamilare Simeon / SimeonJr Studios",
            rewardType = RewardType.CASH_PRIZE,
            prizeValue = "₦50,000 Cash Pool",
            totalPool = "₦50,000",
            remainingBudget = "₦50,000",
            country = "🇳🇬 Nigeria & Global",
            minScoreToQualify = 2500,
            daysRemaining = 12,
            isLive = true,
            sponsorDisclaimer = "Inaugural launch tournament sponsored personally by developer Afolabi Oluwadamilare Simeon.",
            verificationNotice = "Top 5 leaderboard scorers at deadline undergo KYC phone verification for direct bank payout."
        ),
        RewardCampaign(
            id = "rew_global_visa",
            title = "Global Music Trivia Championship",
            sponsorName = "Global Beatbox Alliance",
            rewardType = RewardType.CASH_PRIZE,
            prizeValue = "$100 Digital Card",
            totalPool = "$1,000",
            remainingBudget = "$600",
            country = "🌍 Worldwide (All Regions)",
            minScoreToQualify = 3000,
            daysRemaining = 15,
            isLive = true,
            sponsorDisclaimer = "Digital Visa/Mastercard reward code delivered internationally to qualified players.",
            verificationNotice = "Audited against server logs. Winners notified via registered email."
        ),
        RewardCampaign(
            id = "rew_concert_vip",
            title = "Afro Nation VIP Weekend Passes",
            sponsorName = "LiveFest Music Africa",
            rewardType = RewardType.MERCH_CONCERT,
            prizeValue = "2x VIP Backstage Passes",
            totalPool = "10 Passes",
            remainingBudget = "4 Passes",
            country = "🌍 Global Travel / Lagos",
            minScoreToQualify = 4000,
            daysRemaining = 21,
            isLive = true,
            sponsorDisclaimer = "Official partner festival passes provided by verified entertainment agency.",
            verificationNotice = "Must be 18+ to claim event access tickets. Identity matching leaderboard name required."
        )
    )
}
