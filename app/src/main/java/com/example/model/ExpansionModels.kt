package com.example.model

// ==========================================
// 1. BUSINESS HUB MODELS
// ==========================================

enum class BusinessCategory(val label: String, val iconDescription: String) {
    STUDIOS("Recording Studios", "Pro recording, mixing & rehearsal spaces"),
    EQUIPMENT_RENTAL("Gear & Rentals", "Cameras, microphones, PA systems & lighting"),
    FASHION_STYLING("Wardrobe & Styling", "Music video outfits, designers & stylists"),
    FOOD_CATERING("Catering & Food", "Shoot-day catering, craft services & snacks"),
    MUSIC_TECH("Music Tech & Software", "Distribution, marketing tools & plugins"),
    EVENT_VENUES("Venues & Stages", "Concert venues, pop-up halls & listening rooms")
}

data class BusinessListing(
    val id: String,
    val name: String,
    val tagline: String,
    val category: BusinessCategory,
    val description: String,
    val location: String, // e.g. "Lekki Phase 1, Lagos" or "Johannesburg, SA"
    val rating: Double,
    val reviewsCount: Int,
    val contactPhone: String,
    val whatsapp: String,
    val website: String,
    val email: String,
    val isSponsored: Boolean = false,
    val isVerified: Boolean = true,
    val viewsCount: Int = 1420,
    val enquiriesCount: Int = 89,
    val specialOffer: String? = null, // e.g. "20% off for MUSIZEARN artists"
    val dateAdded: String = "2026-03-01"
)

data class BusinessEnquiry(
    val id: String,
    val businessId: String,
    val businessName: String,
    val clientName: String,
    val clientPhone: String,
    val clientEmail: String,
    val message: String,
    val dateSubmitted: String,
    val isHandled: Boolean = false
)

data class RegisterBusinessRequest(
    val name: String,
    val category: BusinessCategory,
    val description: String,
    val location: String,
    val phone: String,
    val whatsapp: String,
    val email: String,
    val specialOffer: String
)

// ==========================================
// 2. PRODUCER ZONE MODELS
// ==========================================

enum class BeatLicenseType(val title: String, val summary: String, val allowsStreaming: Boolean, val deliversStems: Boolean) {
    FREE_AUDITION("Free Audition / Demo", "Non-profit demo, recording practice only. MusizEarn voice-tag present.", false, false),
    NON_EXCLUSIVE_LEASE("Standard Lease (WAV)", "Up to 100,000 commercial streams, untagged WAV + MP3.", true, false),
    EXCLUSIVE_BUYOUT("Exclusive Rights Buyout", "Sole ownership, unlimited streams, full trackout stems + legal bill of sale. Cannot be resold.", true, true)
}

data class ProducerBeat(
    val id: String,
    val title: String,
    val producerName: String,
    val genre: String, // e.g. "Afrobeats", "Amapiano", "Afro-Drill", "Trap & RnB"
    val bpm: Int,
    val musicalKey: String, // e.g. "F# Minor", "C Major"
    val mood: String, // e.g. "Energetic / Party", "Late Night / Melancholy"
    val coverArtUrl: String = "",
    val playCount: Int = 3840,
    val nonExclusivePriceUsd: Double = 35.0,
    val exclusivePriceUsd: Double = 450.0,
    val isExclusiveSold: Boolean = false,
    val buyerName: String? = null,
    val tags: List<String> = emptyList(),
    val stemsAvailable: Boolean = true
)

data class BeatLicenseTransaction(
    val id: String,
    val beatId: String,
    val beatTitle: String,
    val producerName: String,
    val licenseType: BeatLicenseType,
    val amountPaidUsd: Double,
    val buyerName: String,
    val buyerEmail: String,
    val timestamp: String,
    val licenseCertificateCode: String
)

// ==========================================
// 3. MUSIZEARN ACADEMY MODELS
// ==========================================

enum class AcademyCategory(val label: String) {
    BEAT_PRODUCTION("Beat Production"),
    MIXING_MASTERING("Mixing & Audio"),
    DJ_SKILLS("DJ Skills & Performance"),
    MUSIC_BUSINESS("Music Business & Law"),
    VOCALS_SONGWRITING("Vocals & Songwriting")
}

data class AcademyQuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class AcademyLesson(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val videoPreviewSummary: String,
    val keyTakeaways: List<String>,
    val quizQuestion: AcademyQuizQuestion
)

data class AcademyCourse(
    val id: String,
    val title: String,
    val instructor: String,
    val instructorRole: String,
    val category: AcademyCategory,
    val difficulty: String, // "Beginner", "Intermediate", "Masterclass"
    val durationHours: Double,
    val lessonCount: Int,
    val rating: Double,
    val enrolledCount: Int,
    val overview: String,
    val lessons: List<AcademyLesson>,
    val isCertified: Boolean = true
)

data class AcademyCertificate(
    val id: String,
    val courseId: String,
    val courseTitle: String,
    val studentName: String,
    val issueDate: String,
    val certificateNumber: String,
    val instructorSignature: String = "SimeonJr Studios Academic Council"
)

// ==========================================
// 4. SOUND LIBRARY MODELS
// ==========================================

enum class SoundCategory(val label: String) {
    AFRO_PERCUSSION("Afro Percussion & Congas"),
    EIGHT_O_EIGHT("808s & Sub Bass"),
    DRUM_ONE_SHOTS("Drum One-Shots & Snares"),
    VOCAL_CHOPS("Vocal Chops & Hooks"),
    SYNTH_LOOPS("Melodic Stems & Synths"),
    FOLEY_FX("Foley, Ambience & Transitions")
}

data class SoundAsset(
    val id: String,
    val name: String,
    val creatorName: String,
    val category: SoundCategory,
    val fileFormat: String = "24-bit 48kHz WAV",
    val durationSeconds: Double,
    val bpm: Int? = null,
    val key: String? = null,
    val downloadCount: Int,
    val isRoyaltyFree: Boolean = true,
    val licenseNotice: String = "100% Royalty-Free for Commercial & Streaming Releases under MUSIZEARN Standard Creator License"
)

// ==========================================
// 5. CREATOR SERVICES MARKETPLACE
// ==========================================

enum class ServiceCategory(val label: String) {
    MIXING_MASTERING("Mixing & Mastering Engineers"),
    SESSION_VOCALIST("Session Vocalists & Hooks"),
    SONGWRITER("Professional Songwriters"),
    COVER_ART_DESIGN("Album & Cover Art Designers"),
    MUSIC_VIDEO("Video Editors & Colorists"),
    MUSIC_MARKETING("PR, Pitching & Campaign Strategists")
}

data class CreatorServiceListing(
    val id: String,
    val providerName: String,
    val verifiedBadge: Boolean = true,
    val category: ServiceCategory,
    val title: String, // e.g. "Grammy-Caliber Stem Mixing & Dolby Atmos Mastering"
    val bio: String,
    val location: String,
    val startingRateUsd: Double,
    val turnaroundDays: Int,
    val completedOrders: Int,
    val rating: Double,
    val reviewCount: Int,
    val portfolioHighlights: List<String>,
    val skills: List<String>
)

data class ServiceQuoteRequest(
    val id: String,
    val serviceId: String,
    val providerName: String,
    val clientName: String,
    val clientEmail: String,
    val projectTitle: String,
    val budgetUsd: Double,
    val deadlineDays: Int,
    val projectDescription: String,
    val dateSubmitted: String,
    val isAcknowledged: Boolean = false
)
