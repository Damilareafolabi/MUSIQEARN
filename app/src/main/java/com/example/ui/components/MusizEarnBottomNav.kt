package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.Screen
import com.example.ui.theme.AcidLime
import com.example.ui.theme.CardOliveBorder
import com.example.ui.theme.DarkPillBg
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.TextMuted

private data class NavItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
)

@Composable
fun MusizEarnBottomNav(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(Screen.HOME, "Home", Icons.Default.Home),
        NavItem(Screen.DISCOVER, "Explore", Icons.Default.Explore),
        NavItem(Screen.LEADERBOARDS, "Arena", Icons.Default.Leaderboard),
        NavItem(Screen.REWARDS, "Rewards", Icons.Default.CardGiftcard),
        NavItem(Screen.PROFILE, "Profile", Icons.Default.Person)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        // Floating Pill Dock Container (Screen 2 Reference)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(DarkPillBg)
                .border(1.dp, CardOliveBorder, RoundedCornerShape(32.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentScreen == item.screen ||
                    (item.screen == Screen.HOME && currentScreen == Screen.GAMEPLAY) ||
                    (item.screen == Screen.LEADERBOARDS && currentScreen == Screen.BATTLE) ||
                    (item.screen == Screen.PROFILE && currentScreen == Screen.ARTIST_PORTAL)

                if (isSelected) {
                    // Active Glowing Acid Lime Circular Pill
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(AcidLime)
                            .clickable { onNavigate(item.screen) }
                            .testTag("nav_${item.title.lowercase()}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = ObsidianBlack,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                } else {
                    // Inactive Clean Pill
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .clickable { onNavigate(item.screen) }
                            .testTag("nav_${item.title.lowercase()}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = TextMuted,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}
