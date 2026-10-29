package com.example.data

import com.example.model.LeaderboardEntry

object LeaderboardData {

    val globalRankings: List<LeaderboardEntry> = listOf(
        LeaderboardEntry(1, "AfroKing_Lagos", "Nigeria", "🇳🇬", 4820, 18, badge = "👑"),
        LeaderboardEntry(2, "Jozi_Groover", "South Africa", "🇿🇦", 4690, 15, badge = "🥈"),
        LeaderboardEntry(3, "AccraBeats_Gh", "Ghana", "🇬🇭", 4410, 12, badge = "🥉"),
        LeaderboardEntry(4, "LondonRiddim", "United Kingdom", "🇬🇧", 4150, 9),
        LeaderboardEntry(5, "NairobiVibe", "Kenya", "🇰🇪", 3980, 8),
        LeaderboardEntry(6, "AtlantaTrap_Star", "United States", "🇺🇸", 3820, 7),
        LeaderboardEntry(7, "StarPlayer_Simeon", "Nigeria", "🇳🇬", 3640, 5, isUser = true, badge = "⚡"),
        LeaderboardEntry(8, "TorontoFlow", "Canada", "🇨🇦", 3490, 6),
        LeaderboardEntry(9, "KigaliMelody", "Rwanda", "🇷🇼", 3210, 4),
        LeaderboardEntry(10, "BerlinBassline", "Germany", "🇩🇪", 3100, 3)
    )

    val nigeriaRankings: List<LeaderboardEntry> = listOf(
        LeaderboardEntry(1, "AfroKing_Lagos", "Nigeria", "🇳🇬", 4820, 18, badge = "👑"),
        LeaderboardEntry(2, "Ibadan_Melody", "Nigeria", "🇳🇬", 4320, 14, badge = "🥈"),
        LeaderboardEntry(3, "PortHarcourt_Boy", "Nigeria", "🇳🇬", 4050, 11, badge = "🥉"),
        LeaderboardEntry(4, "StarPlayer_Simeon", "Nigeria", "🇳🇬", 3640, 5, isUser = true, badge = "⚡"),
        LeaderboardEntry(5, "AbujaGroover", "Nigeria", "🇳🇬", 3420, 6),
        LeaderboardEntry(6, "EnuguVibes", "Nigeria", "🇳🇬", 3180, 5),
        LeaderboardEntry(7, "BeninCity_Flow", "Nigeria", "🇳🇬", 2950, 4)
    )

    val southAfricaRankings: List<LeaderboardEntry> = listOf(
        LeaderboardEntry(1, "Jozi_Groover", "South Africa", "🇿🇦", 4690, 15, badge = "👑"),
        LeaderboardEntry(2, "DurbanAmapiano", "South Africa", "🇿🇦", 4120, 11, badge = "🥈"),
        LeaderboardEntry(3, "CapeTownSound", "South Africa", "🇿🇦", 3780, 8, badge = "🥉"),
        LeaderboardEntry(4, "Soweto_Bass", "South Africa", "🇿🇦", 3350, 6)
    )

    val ukRankings: List<LeaderboardEntry> = listOf(
        LeaderboardEntry(1, "LondonRiddim", "United Kingdom", "🇬🇧", 4150, 9, badge = "👑"),
        LeaderboardEntry(2, "ManchesterAfro", "United Kingdom", "🇬🇧", 3620, 7, badge = "🥈"),
        LeaderboardEntry(3, "BrumDrillQueen", "United Kingdom", "🇬🇧", 3290, 5, badge = "🥉")
    )

    val usaRankings: List<LeaderboardEntry> = listOf(
        LeaderboardEntry(1, "AtlantaTrap_Star", "United States", "🇺🇸", 3820, 7, badge = "👑"),
        LeaderboardEntry(2, "BrooklynAfroBeat", "United States", "🇺🇸", 3540, 6, badge = "🥈"),
        LeaderboardEntry(3, "HoustonRhythm", "United States", "🇺🇸", 3110, 4, badge = "🥉")
    )
}
