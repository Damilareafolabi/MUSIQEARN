package com.example.data

import com.example.model.AcademyCategory
import com.example.model.AcademyCourse
import com.example.model.AcademyLesson
import com.example.model.AcademyQuizQuestion
import com.example.model.BusinessCategory
import com.example.model.BusinessListing
import com.example.model.CreatorServiceListing
import com.example.model.ProducerBeat
import com.example.model.ServiceCategory
import com.example.model.SoundAsset
import com.example.model.SoundCategory

object ExpansionData {

    // ==========================================
    // 1. BUSINESS HUB SEED DIRECTORY
    // ==========================================
    val defaultBusinesses: List<BusinessListing> = listOf(
        BusinessListing(
            id = "biz-01",
            name = "Obsidian Sound Lab Lekki",
            tagline = "Grammy-Caliber SSL 4000E & Dolby Atmos Mixing Suite",
            category = BusinessCategory.STUDIOS,
            description = "Lagos premier tracking and spatial audio mixing facility. Equipped with Neumann U87s, Tube-Tech CL1B, Genelec 8351B monitoring and live acoustic drum room. Verified by SimeonJr Studios.",
            location = "Lekki Phase 1, Lagos, Nigeria",
            rating = 4.9,
            reviewsCount = 128,
            contactPhone = "+234 802 555 0192",
            whatsapp = "+2348025550192",
            website = "https://musizearn.com/studios/obsidian-lekki",
            email = "booking@obsidiansoundlab.ng",
            isSponsored = true,
            isVerified = true,
            viewsCount = 3840,
            enquiriesCount = 214,
            specialOffer = "15% off first 8-hour block for registered MUSIZEARN creators"
        ),
        BusinessListing(
            id = "biz-02",
            name = "CineGear Africa Rentals",
            tagline = "RED V-Raptor, ARRI Alexa Mini LF & Cine Lenses",
            category = BusinessCategory.EQUIPMENT_RENTAL,
            description = "Specialized camera and lighting gear rental for music videos, documentary films, and live acoustic performance tapings. Full grip trucks and insured generator sets available.",
            location = "Ikeja GRA, Lagos & Accra, Ghana",
            rating = 4.8,
            reviewsCount = 94,
            contactPhone = "+234 813 444 8820",
            whatsapp = "+2348134448820",
            website = "https://cinegearafrica.com",
            email = "rentals@cinegearafrica.com",
            isSponsored = true,
            isVerified = true,
            viewsCount = 2950,
            enquiriesCount = 160,
            specialOffer = "Free wireless video transmitter kit on bookings of 2+ shoot days"
        ),
        BusinessListing(
            id = "biz-03",
            name = "Alté Threadworks & Styling",
            tagline = "Bespoke Stage Wardrobe & Video Styling Atelier",
            category = BusinessCategory.FASHION_STYLING,
            description = "Avant-garde streetwear, custom leather corsets, tailored agbadas, and futuristic Afropunk stage pieces. Styled leading visual albums for Afrobeats headliners.",
            location = "Victoria Island, Lagos, Nigeria",
            rating = 4.95,
            reviewsCount = 76,
            contactPhone = "+234 703 111 4455",
            whatsapp = "+2347031114455",
            website = "https://altethreadworks.fashion",
            email = "wardrobe@altethreadworks.fashion",
            isSponsored = false,
            isVerified = true,
            viewsCount = 2100,
            enquiriesCount = 118,
            specialOffer = "Complimentary on-set fitting session for EP cover shoots"
        ),
        BusinessListing(
            id = "biz-04",
            name = "SetSnacks & Shoot Craft Gourmet",
            tagline = "Energy-Packed Craft Services for Music Video Crews",
            category = BusinessCategory.FOOD_CATERING,
            description = "Fresh mocktails, healthy wraps, hot jollof platters, espresso bars, and clean energy snacks served on 16-hour video set turnarounds. Kept 200+ production crews fueled.",
            location = "Yaba & Surulere, Lagos, Nigeria",
            rating = 4.85,
            reviewsCount = 112,
            contactPhone = "+234 809 333 7711",
            whatsapp = "+2348093337711",
            website = "https://setsnacksgourmet.ng",
            email = "orders@setsnacksgourmet.ng",
            isSponsored = false,
            isVerified = true,
            viewsCount = 1820,
            enquiriesCount = 95,
            specialOffer = "Complimentary cold-brew espresso bar for overnight set bookings"
        ),
        BusinessListing(
            id = "biz-05",
            name = "GrooveSync Cloud Distro & Pitching",
            tagline = "Automated Royalty Splits & Global Editorial Playlisting",
            category = BusinessCategory.MUSIC_TECH,
            description = "Direct DSP distribution pipes to Spotify, Apple Music, Audiomack, Boomplay and TikTok with automated smart contract splits and DSP pitch deck creation.",
            location = "London, UK & Lagos, Nigeria",
            rating = 4.75,
            reviewsCount = 310,
            contactPhone = "+44 20 7946 0991",
            whatsapp = "+442079460991",
            website = "https://groovesync.io",
            email = "hello@groovesync.io",
            isSponsored = true,
            isVerified = true,
            viewsCount = 5200,
            enquiriesCount = 380,
            specialOffer = "Free 1-year unlimited release plan for Academy graduates"
        ),
        BusinessListing(
            id = "biz-06",
            name = "The Echo Dome & Rooftop Stage",
            tagline = "Acoustically Treated 400-Cap Intimate Concert Space",
            category = BusinessCategory.EVENT_VENUES,
            description = "Intimate live showcase hall with state-of-the-art Line Array PA system, moving DMX lights, live multi-track recording and broadcast-ready multi-cam switcher.",
            location = "Oniru Private Waterfront, Victoria Island, Lagos",
            rating = 4.9,
            reviewsCount = 88,
            contactPhone = "+234 818 900 1234",
            whatsapp = "+2348189001234",
            website = "https://echodomevenue.com",
            email = "events@echodomevenue.com",
            isSponsored = false,
            isVerified = true,
            viewsCount = 3400,
            enquiriesCount = 142,
            specialOffer = "20% off Sunday afternoon listening party rental slots"
        )
    )

    // ==========================================
    // 2. PRODUCER ZONE SEED BEATS
    // ==========================================
    val defaultBeats: List<ProducerBeat> = listOf(
        ProducerBeat(
            id = "beat-01",
            title = "Lagos Midnight Breeze",
            producerName = "Kid Kulture Beats",
            genre = "Afrobeats",
            bpm = 104,
            musicalKey = "F# Minor",
            mood = "Smooth / Sultry / Late Night",
            playCount = 14200,
            nonExclusivePriceUsd = 29.99,
            exclusivePriceUsd = 380.0,
            isExclusiveSold = false,
            tags = listOf("Burna Boy Vibe", "Acoustic Guitar", "Warm Bass", "Bikutsi Shakers")
        ),
        ProducerBeat(
            id = "beat-02",
            title = "Amapiano Storm (Log Drum Ritual)",
            producerName = "DJ Sabelo & SimeonJr",
            genre = "Amapiano",
            bpm = 113,
            musicalKey = "C Minor",
            mood = "Energetic / High Energy / Club",
            playCount = 28600,
            nonExclusivePriceUsd = 34.99,
            exclusivePriceUsd = 490.0,
            isExclusiveSold = false,
            tags = listOf("Deep Log Drum", "Soulful Piano", "Whistle Chants", "Kabza Vibe")
        ),
        ProducerBeat(
            id = "beat-03",
            title = "Gold Coast Sunset",
            producerName = "Nana Kweku Sound",
            genre = "Highlife / Afro-Pop",
            bpm = 98,
            musicalKey = "G Major",
            mood = "Celebratory / Feel-Good",
            playCount = 9840,
            nonExclusivePriceUsd = 24.99,
            exclusivePriceUsd = 290.0,
            isExclusiveSold = false,
            tags = listOf("Clean Horns", "Vintage Guitar", "Palm Wine", "Live Percussion")
        ),
        ProducerBeat(
            id = "beat-04",
            title = "Gidi Drill Protocol",
            producerName = "Ghost808 Lagos",
            genre = "Afro-Drill",
            bpm = 142,
            musicalKey = "D Minor",
            mood = "Dark / Aggressive / Hype",
            playCount = 18300,
            nonExclusivePriceUsd = 39.99,
            exclusivePriceUsd = 520.0,
            isExclusiveSold = false,
            tags = listOf("Sliding 808", "Dark Melodies", "Crisp Snares", "UK x Lagos")
        ),
        ProducerBeat(
            id = "beat-05",
            title = "Celestial Waves (R&B Soul)",
            producerName = "Afolabi Audio Labs",
            genre = "Afro-R&B",
            bpm = 88,
            musicalKey = "Eb Major",
            mood = "Emotional / Intimate / Atmospheric",
            playCount = 12450,
            nonExclusivePriceUsd = 32.0,
            exclusivePriceUsd = 420.0,
            isExclusiveSold = false,
            tags = listOf("Tems Style", "Lush Rhodes", "Reverb Vocals", "Airy Pads")
        )
    )

    // ==========================================
    // 3. MUSIZEARN ACADEMY SEED COURSES
    // ==========================================
    val defaultAcademyCourses: List<AcademyCourse> = listOf(
        AcademyCourse(
            id = "course-01",
            title = "Afrobeats & Amapiano Masterclass: From Drums to Stems",
            instructor = "Afolabi Simeon & Guest Master Producers",
            instructorRole = "Lead Sound Architect & Platinum Beatmaker",
            category = AcademyCategory.BEAT_PRODUCTION,
            difficulty = "Beginner to Pro",
            durationHours = 3.5,
            lessonCount = 5,
            rating = 4.96,
            enrolledCount = 4210,
            overview = "Learn the authentic drum swing, complex syncopated percussion, log drum envelope manipulation, and melody layering that powers contemporary African chart-toppers.",
            lessons = listOf(
                AcademyLesson(
                    id = "les-01",
                    title = "Anatomy of the Afrobeats Groove & Micro-Swing",
                    durationMinutes = 24,
                    videoPreviewSummary = "Dissecting 3:2 clave rhythms, 16th-note shaker humanization, and avoiding stiff DAW quantizing.",
                    keyTakeaways = listOf(
                        "Never hard-quantize shakers to 100% on the grid; apply 15-25% triplet swing.",
                        "Layer a dry rimshot with a warm woodblock for the authentic modern Afrobeats snare timbre.",
                        "Use high-pass filters on hand percussions to leave space for sub frequencies."
                    ),
                    quizQuestion = AcademyQuizQuestion(
                        id = "q-01",
                        question = "What is the recommended practice for programming Afrobeats shakers to achieve natural human feel?",
                        options = listOf(
                            "Quantize every note strictly to 100% on the straight 1/16 grid",
                            "Apply subtle micro-timing swing (15-25%) and slight velocity variations",
                            "Remove all velocity dynamics so all notes hit at 127 velocity",
                            "Mute the low end completely and boost 15kHz by +12dB"
                        ),
                        correctIndex = 1,
                        explanation = "Organic swing and human velocity dynamics give African rhythms their signature bounce and groove."
                    )
                ),
                AcademyLesson(
                    id = "les-02",
                    title = "The Legendary Amapiano Log Drum: Synthesis & Distortion",
                    durationMinutes = 32,
                    videoPreviewSummary = "Using frequency modulation and pitch envelope pitch drop to create punchy, floor-shaking log drum drops.",
                    keyTakeaways = listOf(
                        "The log drum is modeled on DX7 FM synth bass algorithms with rapid pitch decay.",
                        "Mono the bass below 120Hz to prevent stereo phase cancellation in club sound systems.",
                        "Layer subtle tube saturation to create audible mid-frequency harmonics for phone speakers."
                    ),
                    quizQuestion = AcademyQuizQuestion(
                        id = "q-02",
                        question = "Why should the sub-bass frequencies below 120Hz in an Amapiano track be kept strictly in mono?",
                        options = listOf(
                            "Because stereo widening below 120Hz causes phase cancellation and weak bass on club PAs",
                            "Because mono audio files take up zero storage space",
                            "Because streaming services automatically delete stereo bass tracks",
                            "Because high frequencies only exist in mono"
                        ),
                        correctIndex = 0,
                        explanation = "Sub frequencies under 120Hz must remain mono to prevent phase cancellation and maximize physical impact on club sound systems."
                    )
                ),
                AcademyLesson(
                    id = "les-03",
                    title = "Exporting Trackout Stems for International Mix Engineers",
                    durationMinutes = 20,
                    videoPreviewSummary = "Standard gain staging, printing wet/dry stems, naming conventions, and sample rate standards.",
                    keyTakeaways = listOf(
                        "Always export 24-bit 44.1kHz or 48kHz uncompressed WAV files.",
                        "Leave at least -6dB headroom on the master bus without limiters on the export.",
                        "Provide dry vocal and dry instrument stems alongside processed FX stems."
                    ),
                    quizQuestion = AcademyQuizQuestion(
                        id = "q-03",
                        question = "What is the ideal peak headroom to leave on an unmastered track mix export?",
                        options = listOf(
                            "+3dB above 0 with a hard clipping plugin",
                            "At least -6dB to -4dB true peak with master limiters deactivated",
                            "-30dB so that no sound can be heard",
                            "0.0dB with maximum ceiling compression"
                        ),
                        correctIndex = 1,
                        explanation = "Leaving -6dB of headroom ensures the mastering engineer has adequate dynamic range to apply EQ, analog saturation, and multi-band compression."
                    )
                )
            )
        ),
        AcademyCourse(
            id = "course-02",
            title = "Music Business & Royalty Engineering: Splits, PROs & Sync",
            instructor = "Barrister T. Adeleke & Industry Executives",
            instructorRole = "Entertainment Attorney & Royalty Auditor",
            category = AcademyCategory.MUSIC_BUSINESS,
            difficulty = "All Creators",
            durationHours = 2.8,
            lessonCount = 4,
            rating = 4.98,
            enrolledCount = 3890,
            overview = "Never lose your money. Master publishing rights, mechanical royalties, master royalties, PRO registration (BMI/ASCAP/SAMRO/COSON), and sync licensing for Netflix/games.",
            lessons = listOf(
                AcademyLesson(
                    id = "les-bus-01",
                    title = "Master Rights vs. Publishing Rights Demystified",
                    durationMinutes = 28,
                    videoPreviewSummary = "Understand the two halves of every song copyright: the sound recording vs the underlying composition.",
                    keyTakeaways = listOf(
                        "The Master copyright belongs to the person who funded/recorded the physical sound recording.",
                        "The Composition (Publishing) copyright belongs 50% to lyrics and 50% to the melody/musical score.",
                        "Always sign a split sheet before leaving the recording studio session."
                    ),
                    quizQuestion = AcademyQuizQuestion(
                        id = "q-bus-01",
                        question = "What two distinct copyrights exist in every legally recognized musical song?",
                        options = listOf(
                            "The Master Sound Recording copyright and the Musical Composition / Publishing copyright",
                            "The TikTok copyright and the Instagram Reel copyright",
                            "The Phone Ringtone copyright and the CD Print copyright",
                            "The YouTube View copyright and the Stage Microphone copyright"
                        ),
                        correctIndex = 0,
                        explanation = "Every commercial track has two separate legal copyrights: the Master (sound recording) and the Composition (music and lyrics)."
                    )
                ),
                AcademyLesson(
                    id = "les-bus-02",
                    title = "Split Sheets: The Contract Every Studio Session Needs",
                    durationMinutes = 18,
                    videoPreviewSummary = "How to write, sign, and store digital split agreements on the day of production to avoid future lawsuits.",
                    keyTakeaways = listOf(
                        "Split sheets must total exactly 100% across all contributing songwriters and producers.",
                        "Include CAE/IPI numbers, legal names, and publishing affiliations.",
                        "Digital e-signatures with timestamps are legally enforceable worldwide."
                    ),
                    quizQuestion = AcademyQuizQuestion(
                        id = "q-bus-02",
                        question = "When is the most critical time to negotiate and sign a musical split sheet?",
                        options = listOf(
                            "Five years after the song hits #1 on the Billboard charts",
                            "During or immediately at the conclusion of the recording session while everyone is present",
                            "Only after an international lawsuit is filed",
                            "Never, verbal agreements are always universally respected"
                        ),
                        correctIndex = 1,
                        explanation = "Memories fade and disputes arise quickly once money arrives. Completing split sheets during or right after the session eliminates future legal wars."
                    )
                )
            )
        )
    )

    // ==========================================
    // 4. SOUND LIBRARY SEED ASSETS
    // ==========================================
    val defaultSoundAssets: List<SoundAsset> = listOf(
        SoundAsset(
            id = "snd-01",
            name = "Lekki Heavy Log Drum 01 (F#)",
            creatorName = "SimeonJr Sound Labs",
            category = SoundCategory.EIGHT_O_EIGHT,
            fileFormat = "24-bit 48kHz WAV",
            durationSeconds = 2.4,
            bpm = 113,
            key = "F#",
            downloadCount = 8920
        ),
        SoundAsset(
            id = "snd-02",
            name = "Calabar Talking Drum Roll (Live)",
            creatorName = "Master Drummer Ekpe",
            category = SoundCategory.AFRO_PERCUSSION,
            fileFormat = "24-bit 48kHz WAV",
            durationSeconds = 4.1,
            bpm = 105,
            key = null,
            downloadCount = 6340
        ),
        SoundAsset(
            id = "snd-03",
            name = "Warm Rhodes Piano Chords (Neo-Afro)",
            creatorName = "Afolabi Keys",
            category = SoundCategory.SYNTH_LOOPS,
            fileFormat = "24-bit 48kHz WAV",
            durationSeconds = 8.0,
            bpm = 100,
            key = "Db Major",
            downloadCount = 7410
        ),
        SoundAsset(
            id = "snd-04",
            name = "High-Energy Vocal Chant 'Oshey!'",
            creatorName = "Lagos Street Vocals",
            category = SoundCategory.VOCAL_CHOPS,
            fileFormat = "24-bit 48kHz WAV",
            durationSeconds = 1.2,
            bpm = null,
            key = "A Minor",
            downloadCount = 11200
        ),
        SoundAsset(
            id = "snd-05",
            name = "Crispy Woodblock Rimshot Snare",
            creatorName = "Kid Kulture",
            category = SoundCategory.DRUM_ONE_SHOTS,
            fileFormat = "24-bit 48kHz WAV",
            durationSeconds = 0.8,
            bpm = null,
            key = null,
            downloadCount = 9810
        ),
        SoundAsset(
            id = "snd-06",
            name = "Balogun Market Ambience & Rain Foley",
            creatorName = "AfroFoley Field Audio",
            category = SoundCategory.FOLEY_FX,
            fileFormat = "24-bit 48kHz WAV",
            durationSeconds = 12.5,
            bpm = null,
            key = null,
            downloadCount = 4200
        )
    )

    // ==========================================
    // 5. CREATOR SERVICES MARKETPLACE SEED
    // ==========================================
    val defaultCreatorServices: List<CreatorServiceListing> = listOf(
        CreatorServiceListing(
            id = "srv-01",
            providerName = "Engr. Damilare SoundCraft",
            verifiedBadge = true,
            category = ServiceCategory.MIXING_MASTERING,
            title = "Radio-Ready Afrobeats & Hip-Hop Mixing with Dolby Atmos",
            bio = "Over 12 years mixing multi-platinum chart singles across West Africa and the UK. Certified Avid Pro Tools & SSL mixing engineer. Guarantees loud, dynamic, punchy mixes.",
            location = "Lagos / Remote Worldwide",
            startingRateUsd = 120.0,
            turnaroundDays = 3,
            completedOrders = 342,
            rating = 4.97,
            reviewCount = 280,
            portfolioHighlights = listOf("3 Billboard Charting Mixes", "Over 80 Million Combined Spotify Streams", "Top-Tier Analog Outboard Chain"),
            skills = listOf("Pro Tools", "SSL G-Master Bus", "Tube-Tech CL1B", "Dolby Atmos 7.1.4", "Vocal Tuning")
        ),
        CreatorServiceListing(
            id = "srv-02",
            providerName = "Zara Sunshine Vocals",
            verifiedBadge = true,
            category = ServiceCategory.SESSION_VOCALIST,
            title = "Soulful Afropop Female Lead Vocals, Harmonies & Ad-Libs",
            bio = "Warm, versatile alto/soprano vocalist specializing in bilingual English, Yoruba, and Pidgin vocal hooks. Professional acoustically treated home studio equipped with Neumann TLM 103.",
            location = "London / Remote Worldwide",
            startingRateUsd = 95.0,
            turnaroundDays = 2,
            completedOrders = 189,
            rating = 4.94,
            reviewCount = 145,
            portfolioHighlights = listOf("Featured on BBC 1Xtra Afrobeats Show", "Provided backing choir for major arena live tour"),
            skills = listOf("Lead Vocals", "4-Part Vocal Harmonies", "Ad-Libs", "Bilingual Songwriting", "Neumann Mic")
        ),
        CreatorServiceListing(
            id = "srv-03",
            providerName = "Kolawole Visual Concepts",
            verifiedBadge = true,
            category = ServiceCategory.COVER_ART_DESIGN,
            title = "Iconic 3D & Mixed-Media Album Art, Canvas Animations & Merch",
            bio = "Visual artist creating unforgettable album sleeves and animated Spotify Canvas assets for independent and signed recording artists. Combines 3D Blender sculpting with Afro-futuristic oil textures.",
            location = "Abuja & Remote Worldwide",
            startingRateUsd = 75.0,
            turnaroundDays = 3,
            completedOrders = 265,
            rating = 4.98,
            reviewCount = 210,
            portfolioHighlights = listOf("Designed 14 Official Spotify Editorial Covers", "Official Merch lines for major African festivals"),
            skills = listOf("3D Blender", "Spotify Canvas Video", "Print-Ready Vinyl Sleeves", "Typography Design", "Afro-Futurism")
        ),
        CreatorServiceListing(
            id = "srv-04",
            providerName = "EchoPulse Media PR",
            verifiedBadge = true,
            category = ServiceCategory.MUSIC_MARKETING,
            title = "Global DSP Pitching, Apple Music / Spotify Playlist Strategy & Press",
            bio = "Strategic music campaign management firm. We help independent creators build sustainable fan communities, get placed on curated playlists, and secure tastemaker blog writeups.",
            location = "Lagos, London & New York",
            startingRateUsd = 150.0,
            turnaroundDays = 7,
            completedOrders = 175,
            rating = 4.88,
            reviewCount = 130,
            portfolioHighlights = listOf("Placed 60+ Indie Artists on Official DSP Playlists", "Featured in The Fader, Native Mag & OkayAfrica"),
            skills = listOf("DSP Editorial Pitching", "TikTok Sound Seeding", "Press Release Distribution", "Metadata & ISRC Audits")
        )
    )
}
