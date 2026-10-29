package com.example.data

import com.example.model.AdminReport
import com.example.model.BookingRequest
import com.example.model.BookingStatus
import com.example.model.CelebrityBookingProfile
import com.example.model.CelebrityFanClub
import com.example.model.CrowdfundingCampaign
import com.example.model.DirectChatMessage
import com.example.model.FanClubPost

object PlatformData {

    val fanClubs = listOf(
        CelebrityFanClub(
            id = "fc-burna",
            celebrityName = "Burna Boy",
            handle = "@burnaboygram",
            roleTitle = "Grammy Award-winning Global Icon",
            bio = "Official Fan Club for the African Giant. Exclusive updates, tour VIP passes, and fan challenges.",
            country = "Nigeria",
            flag = "🇳🇬",
            followersCount = 428000,
            isFollowing = true,
            posts = listOf(
                FanClubPost(
                    id = "post-1",
                    celebrityId = "fc-burna",
                    celebrityName = "Burna Boy",
                    celebrityAvatar = "BB",
                    content = "London Stadium sold out! To celebrate the Outsiders family, we are giving away 50,000 Airtime vouchers to the top 20 quiz masters on MUSIZEARN this Friday! 🔥",
                    timestamp = "2 hours ago",
                    likesCount = 1420,
                    commentsCount = 389,
                    sponsoredRewardBadge = "Sponsored 50k Airtime Giveaway"
                ),
                FanClubPost(
                    id = "post-2",
                    celebrityId = "fc-burna",
                    celebrityName = "Burna Boy",
                    celebrityAvatar = "BB",
                    content = "New stadium rehearsal snippet live in the Audio player! Can you guess the tempo and opening bassline in under 10 seconds? Drop your high score below.",
                    timestamp = "1 day ago",
                    likesCount = 2890,
                    commentsCount = 612
                )
            )
        ),
        CelebrityFanClub(
            id = "fc-tems",
            celebrityName = "Tems",
            handle = "@temsbaby",
            roleTitle = "Singer, Songwriter & Global Producer",
            bio = "Leading Vibe Tribe worldwide. Direct acoustic sessions, exclusive Q&As, and music trivia tournaments.",
            country = "Nigeria",
            flag = "🇳🇬",
            followersCount = 312000,
            isFollowing = false,
            posts = listOf(
                FanClubPost(
                    id = "post-3",
                    celebrityId = "fc-tems",
                    celebrityName = "Tems",
                    celebrityAvatar = "TM",
                    content = "To every young woman producing music in their bedroom in Lagos, keep believing. Sponsoring 5 new Donate For Me studio campaigns this week on MUSIZEARN. Keep creating ✨",
                    timestamp = "5 hours ago",
                    likesCount = 3450,
                    commentsCount = 420,
                    sponsoredRewardBadge = "Artist Grant Support"
                )
            )
        ),
        CelebrityFanClub(
            id = "fc-asake",
            celebrityName = "Asake",
            handle = "@asakemusic",
            roleTitle = "Mr Money With The Vibe",
            bio = "Fuji-infused Amapiano high energy. Catch all exclusive pop-up dates, fan quizzes, and merch drops.",
            country = "Nigeria",
            flag = "🇳🇬",
            followersCount = 289000,
            isFollowing = true,
            posts = listOf(
                FanClubPost(
                    id = "post-4",
                    celebrityId = "fc-asake",
                    celebrityName = "Asake",
                    celebrityAvatar = "AS",
                    content = "Lungu Boy tour dates dropping next week! The fan club tournament champion wins 2 All-Access VIP backstage wristbands + 10GB Data bundle! 🚀",
                    timestamp = "Yesterday",
                    likesCount = 4120,
                    commentsCount = 740,
                    sponsoredRewardBadge = "VIP Tour Passes"
                )
            )
        ),
        CelebrityFanClub(
            id = "fc-rema",
            celebrityName = "Rema",
            handle = "@heisrema",
            roleTitle = "Afrorave Pioneer",
            bio = "Welcome to the Rave & Roses realm. Global music games, remix contests, and community battles.",
            country = "Nigeria",
            flag = "🇳🇬",
            followersCount = 398000,
            isFollowing = false,
            posts = listOf(
                FanClubPost(
                    id = "post-5",
                    celebrityId = "fc-rema",
                    celebrityName = "Rema",
                    celebrityAvatar = "RM",
                    content = "Calm Down reached another billion milestones! Thank you Ravers. 1v1 Battle Arena will feature only Afrorave stems tonight with double MusizCoins rewards!",
                    timestamp = "3 days ago",
                    likesCount = 5980,
                    commentsCount = 890
                )
            )
        )
    )

    val crowdfundingCampaigns = listOf(
        CrowdfundingCampaign(
            id = "cf-1",
            artistName = "Zinolee Sounds (Tobi Ade)",
            artistCountry = "Nigeria",
            artistFlag = "🇳🇬",
            title = "Debut EP Studio Mixing & Vinyl Pressing",
            purposeCategory = "Recording & Production",
            story = "I am a self-taught producer and vocalist from Surulere, Lagos. I have written 6 Afrobeats and Highlife tracks exploring modern youth resilience. This campaign funds final mixing, mastering by a top sound engineer, and sample licensing for the project.",
            targetAmountUsd = 2500.0,
            raisedAmountUsd = 1820.0,
            supportersCount = 78,
            daysRemaining = 12,
            verifiedCreator = true,
            perksDescription = "Contributors of \$25+ get their name in the album liner notes, early MP3 master downloads, and exclusive in-game avatar badges on MUSIZEARN.",
            updates = listOf(
                "3 tracks fully recorded at Mainframe Studios!",
                "First snippet published in MUSIZEARN Discover feed - listen now!"
            )
        ),
        CrowdfundingCampaign(
            id = "cf-2",
            artistName = "Amina K (Voice of Nairobi)",
            artistCountry = "Kenya",
            artistFlag = "🇰🇪",
            title = "Acoustic Live Session & Music Video in Rift Valley",
            purposeCategory = "Music Video",
            story = "Filming a cinematic visual for my viral single 'Nyota Yangu'. We are collaborating with local visual storytellers in Nakuru to showcase East African traditional string instruments blended with R&B.",
            targetAmountUsd = 1800.0,
            raisedAmountUsd = 1450.0,
            supportersCount = 63,
            daysRemaining = 8,
            verifiedCreator = true,
            perksDescription = "Supporters get behind-the-scenes documentary access and early premiere ticket invitations.",
            updates = listOf(
                "Location permits secured in Great Rift Valley!",
                "Camera gear rental deposit verified."
            )
        ),
        CrowdfundingCampaign(
            id = "cf-3",
            artistName = "Kweku Rhythm (Accra Waves)",
            artistCountry = "Ghana",
            artistFlag = "🇬🇭",
            title = "Studio Monitor Speakers & Vocal Isolation Booth",
            purposeCategory = "Studio Equipment",
            story = "Building an open home studio in Osu, Accra where upcoming highlife and drill artists can record clean vocals for free. Equipment breakdown is fully documented.",
            targetAmountUsd = 1200.0,
            raisedAmountUsd = 890.0,
            supportersCount = 45,
            daysRemaining = 19,
            verifiedCreator = true,
            perksDescription = "Supporters receive free lifetime sample packs and stem libraries recorded in the booth.",
            updates = listOf(
                "Acoustic paneling materials purchased from local timber market."
            )
        )
    )

    val celebrityBookings = listOf(
        CelebrityBookingProfile(
            id = "book-1",
            celebrityName = "Burna Boy & The Outsiders Band",
            specialty = "Afro-Fusion / Festival & Arena Headline",
            country = "Nigeria",
            flag = "🇳🇬",
            managerContact = "Spaceship Collective Management (verified)",
            startingFeeEstimate = "\$150,000+ (Festival / Stadium)",
            rating = 5.0f,
            completedBookings = 82,
            acceptedEventTypes = listOf("Festival Headline", "Stadium Concert", "Global Tour Leg", "Major Award Show")
        ),
        CelebrityBookingProfile(
            id = "book-2",
            celebrityName = "DJ Spinall (The Cap)",
            specialty = "Celebrity DJ, Party Curator & Producer",
            country = "Nigeria",
            flag = "🇳🇬",
            managerContact = "The Cap Music Agency (verified)",
            startingFeeEstimate = "\$15,000 - \$35,000",
            rating = 4.9f,
            completedBookings = 140,
            acceptedEventTypes = listOf("Club Residency", "Wedding & Private Gala", "Festival DJ Set", "Corporate Launch")
        ),
        CelebrityBookingProfile(
            id = "book-3",
            celebrityName = "Ayra Starr",
            specialty = "Afropop, Celestial R&B Live Vocals",
            country = "Nigeria",
            flag = "🇳🇬",
            managerContact = "Mavin Global Booking Office (verified)",
            startingFeeEstimate = "\$45,000 - \$80,000",
            rating = 4.9f,
            completedBookings = 64,
            acceptedEventTypes = listOf("University Concert", "Music Festival", "Brand Ambassadorship", "Private Showcase")
        ),
        CelebrityBookingProfile(
            id = "book-4",
            celebrityName = "Major League DJz",
            specialty = "Balcony Mix Xperience / Amapiano Duo",
            country = "South Africa",
            flag = "🇿🇦",
            managerContact = "Pianochella Touring LTD (verified)",
            startingFeeEstimate = "\$25,000 - \$50,000",
            rating = 4.8f,
            completedBookings = 112,
            acceptedEventTypes = listOf("Amapiano Festival", "Club Tour", "Pop-up Event", "Brand Activation")
        )
    )

    val sampleBookingRequests = listOf(
        BookingRequest(
            id = "req-101",
            celebrityName = "DJ Spinall (The Cap)",
            organizerName = "Lagos Tech Fest 2026",
            organizerEmail = "events@lagostechfest.ng",
            eventType = "Corporate Tech Gala & After-Party",
            eventDate = "December 18, 2026",
            venueCity = "Eko Hotel Convention Centre, Lagos",
            expectedAudience = "2,500 attendees",
            budgetOffer = "\$20,000",
            specialRequirements = "2-hour high-tempo Afrobeats & Amapiano set with full lighting sync",
            status = BookingStatus.IN_REVIEW,
            dateSubmitted = "Oct 24, 2026"
        )
    )

    val communityChatMessages = listOf(
        DirectChatMessage(
            id = "msg-1",
            senderName = "Afolabi (SimeonJr)",
            messageText = "Welcome to MUSIZEARN Global Community! Compete fairly, discover talented indie musicians, and test your music knowledge.",
            timestamp = "10:00 AM",
            isFromMe = false
        ),
        DirectChatMessage(
            id = "msg-2",
            senderName = "AfrobeatsDiva_Lagos",
            messageText = "Just beat 5 players in the 1v1 Battle Arena! Anyone ready for a 90s Highlife trivia match?",
            timestamp = "10:14 AM",
            isFromMe = false
        ),
        DirectChatMessage(
            id = "msg-3",
            senderName = "You",
            messageText = "Challenge accepted! Sending my MUSIZ battle code now 🔥",
            timestamp = "10:15 AM",
            isFromMe = true
        )
    )

    val adminReports = listOf(
        AdminReport(
            id = "rep-001",
            reportCategory = "Music Rights Dispute",
            reportedTarget = "Submission: 'Lagos Nightclub Rhythms'",
            reporterName = "Premier Music Publishing Legal",
            reasonDetail = "Claimant alleges baseline uses an uncredited master sample from a 1983 record. Audio paused pending clearance proof.",
            status = "PENDING_REVIEW",
            date = "Oct 27, 2026"
        ),
        AdminReport(
            id = "rep-002",
            reportCategory = "Spam / Auto-Clicker in Trivia",
            reportedTarget = "User: 'FastBot_778'",
            reporterName = "Automated Fraud Engine",
            reasonDetail = "Answer latency under 45ms across 30 consecutive questions with identical jitter.",
            status = "RESOLVED",
            date = "Oct 28, 2026"
        )
    )
}
