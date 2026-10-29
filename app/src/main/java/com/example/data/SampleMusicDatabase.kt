package com.example.data

import com.example.model.EmergingArtist
import com.example.model.GameMode
import com.example.model.SongQuestion

object SampleMusicDatabase {

    val questions: List<SongQuestion> = listOf(
        SongQuestion(
            id = "q_1",
            title = "City Boys",
            artist = "Burna Boy",
            genre = "Afrobeats",
            year = "2023",
            snippetRiffId = "afrobeats_city_boys",
            options = listOf("City Boys", "Last Last", "Ye", "It's Plenty"),
            correctIndex = 0,
            lyricsSnippet = "Turn around, make you look at me now...",
            funFact = "Sampled J. Cole and won widespread acclaim on Burna Boy's 'I Told Them...' album."
        ),
        SongQuestion(
            id = "q_2",
            title = "Tshwala Bam",
            artist = "TitoM & Yuppe ft. S.N.E & EeQue",
            genre = "Amapiano",
            year = "2024",
            snippetRiffId = "amapiano_tshwala_bam",
            options = listOf("Mnike", "Tshwala Bam", "Amanikiniki", "Water"),
            correctIndex = 1,
            lyricsSnippet = "Tshwala bam, tshwala bam...",
            funFact = "Spawned a viral dance challenge globally with millions of videos across Africa, Europe, and the Americas."
        ),
        SongQuestion(
            id = "q_3",
            title = "Blinding Lights",
            artist = "The Weeknd",
            genre = "Synthpop / 80s Retro",
            year = "2020",
            snippetRiffId = "pop_blinding_lights",
            options = listOf("Starboy", "Save Your Tears", "Blinding Lights", "Can't Feel My Face"),
            correctIndex = 2,
            lyricsSnippet = "I said, ooh, I'm blinded by the lights...",
            funFact = "Ranked by Billboard as the #1 Greatest Hot 100 Hit of All Time, spending over 90 weeks on the chart."
        ),
        SongQuestion(
            id = "q_4",
            title = "Not Like Us",
            artist = "Kendrick Lamar",
            genre = "Hip-Hop",
            year = "2024",
            snippetRiffId = "hiphop_not_like_us",
            options = listOf("HUMBLE.", "Not Like Us", "DNA.", "King Kunta"),
            correctIndex = 1,
            lyricsSnippet = "Psst, I see dead people...",
            funFact = "Produced by Mustard, it shattered West Coast rap streaming records and debuted at #1 on Billboard."
        ),
        SongQuestion(
            id = "q_5",
            title = "Ku Lo Sa",
            artist = "Oxlade",
            genre = "Afropop / Highlife",
            year = "2022",
            snippetRiffId = "highlife_ku_lo_sa",
            options = listOf("Ku Lo Sa", "Love Nwantiti", "Soweto", "Calm Down"),
            correctIndex = 0,
            lyricsSnippet = "Omo, I want make we dey, Ku lo sa, closer...",
            funFact = "Performed live for Colors Studios in Berlin, turning into an instant worldwide streaming sensation."
        ),
        SongQuestion(
            id = "q_6",
            title = "Last Last",
            artist = "Burna Boy",
            genre = "Afrobeats",
            year = "2022",
            snippetRiffId = "afrobeats_last_last",
            options = listOf("On The Low", "Last Last", "Gbona", "Kilometre"),
            correctIndex = 1,
            lyricsSnippet = "E don cast, last last, na everybody go chop breakfast...",
            funFact = "Contains a licensed sample of Toni Braxton's 2000 hit 'He Wasn't Man Enough'."
        ),
        SongQuestion(
            id = "q_7",
            title = "Cuff It",
            artist = "Beyoncé",
            genre = "R&B / Disco-Funk",
            year = "2022",
            snippetRiffId = "rnb_cuff_it",
            options = listOf("Break My Soul", "Cuff It", "Alien Superstar", "Halo"),
            correctIndex = 1,
            lyricsSnippet = "I wanna go where nobody's been, have you ever had fun like this?",
            funFact = "Co-written with Nile Rodgers of Chic, winning the Grammy Award for Best R&B Song in 2023."
        ),
        SongQuestion(
            id = "q_8",
            title = "Three Little Birds",
            artist = "Bob Marley & The Wailers",
            genre = "Reggae",
            year = "1977",
            snippetRiffId = "reggae_three_birds",
            options = listOf("One Love", "No Woman, No Cry", "Three Little Birds", "Is This Love"),
            correctIndex = 2,
            lyricsSnippet = "Don't worry about a thing, 'cause every little thing is gonna be alright...",
            funFact = "Marley was inspired by three pelicans that used to visit the windowsill of his home in Kingston."
        ),
        SongQuestion(
            id = "q_9",
            title = "Calm Down",
            artist = "Rema",
            genre = "Afrorave / Pop",
            year = "2022",
            snippetRiffId = "afrobeats_city_boys",
            options = listOf("Dumebi", "Soundgasm", "Calm Down", "Charm"),
            correctIndex = 2,
            lyricsSnippet = "Baby, calm down, calm down, girl, this your body e put my heart for lockdown...",
            funFact = "Became the highest-charting and longest-charting African song in Billboard Hot 100 history."
        ),
        SongQuestion(
            id = "q_10",
            title = "Water",
            artist = "Tyla",
            genre = "Amapiano / Pop",
            year = "2023",
            snippetRiffId = "amapiano_ke_star",
            options = listOf("Water", "Truth or Dare", "Jump", "Been Thinking"),
            correctIndex = 0,
            lyricsSnippet = "Make me sweat, make me hotter, make me lose my breath, make me water...",
            funFact = "Won the inaugural Grammy Award for Best African Music Performance in 2024."
        )
    )

    val emergingArtists: List<EmergingArtist> = listOf(
        EmergingArtist(
            id = "art_1",
            stageName = "Tayo Vibe",
            realName = "Omotayo Balogun",
            country = "Nigeria",
            flag = "🇳🇬",
            genre = "Afro-Fusion / Street Pop",
            bio = "Lagos-born producer and vocal powerhouse pioneering melodic Yoruba fusion with contemporary synth basslines.",
            followerCount = "18.4K",
            featuredTrackTitle = "Midnight in Surulere",
            snippetRiffId = "afrobeats_city_boys",
            streamSpotifyUrl = "https://open.spotify.com",
            streamAppleUrl = "https://music.apple.com",
            streamAudiomackUrl = "https://audiomack.com",
            streamYoutubeUrl = "https://youtube.com",
            activeChallengesCount = 3
        ),
        EmergingArtist(
            id = "art_2",
            stageName = "Zuri Moon",
            realName = "Zuri Mthembu",
            country = "South Africa",
            flag = "🇿🇦",
            genre = "Soulful Amapiano",
            bio = "Johannesburg vocalist crafting deep, hypnotic log-drum ballads with jazz chord extensions.",
            followerCount = "24.9K",
            featuredTrackTitle = "Soweto Golden Hour",
            snippetRiffId = "amapiano_tshwala_bam",
            streamSpotifyUrl = "https://open.spotify.com",
            streamAppleUrl = "https://music.apple.com",
            streamAudiomackUrl = "https://audiomack.com",
            streamYoutubeUrl = "https://youtube.com",
            activeChallengesCount = 2
        ),
        EmergingArtist(
            id = "art_3",
            stageName = "Kwame Pulse",
            realName = "Kwame Asante",
            country = "Ghana",
            flag = "🇬🇭",
            genre = "Afro-Highlife & Drill",
            bio = "Kumasi native blending intricate palm-wine acoustic guitars with punchy modern 808 percussion.",
            followerCount = "12.7K",
            featuredTrackTitle = "Accra Breeze",
            snippetRiffId = "highlife_ku_lo_sa",
            streamSpotifyUrl = "https://open.spotify.com",
            streamAppleUrl = "https://music.apple.com",
            streamAudiomackUrl = "https://audiomack.com",
            streamYoutubeUrl = "https://youtube.com",
            activeChallengesCount = 4
        ),
        EmergingArtist(
            id = "art_4",
            stageName = "Nala Ray",
            realName = "Nala Wanjiru",
            country = "Kenya",
            flag = "🇰🇪",
            genre = "Afro-R&B / Gengetone Soul",
            bio = "Nairobi songstress celebrated for dreamy acoustic harmonies and uplifting East African rhythmic cadence.",
            followerCount = "15.1K",
            featuredTrackTitle = "Kilimani Sunset",
            snippetRiffId = "rnb_cuff_it",
            streamSpotifyUrl = "https://open.spotify.com",
            streamAppleUrl = "https://music.apple.com",
            streamAudiomackUrl = "https://audiomack.com",
            streamYoutubeUrl = "https://youtube.com",
            activeChallengesCount = 2
        )
    )

    fun getQuestionsForMode(mode: GameMode, count: Int = 5): List<SongQuestion> {
        val pool = when (mode) {
            GameMode.NAME_ARTIST -> questions.map { q ->
                // For "Name the Artist", the question options should be 4 artist names!
                val otherArtists = questions.map { it.artist }.filter { it != q.artist }.distinct().shuffled().take(3)
                val allArtists = (otherArtists + q.artist).shuffled()
                q.copy(
                    options = allArtists,
                    correctIndex = allArtists.indexOf(q.artist)
                )
            }
            GameMode.MISSING_LYRICS -> questions.filter { it.lyricsSnippet.isNotEmpty() }
            GameMode.GENRE_TRIVIA -> questions.shuffled()
            else -> questions.shuffled()
        }
        return pool.take(count.coerceAtMost(pool.size))
    }
}
