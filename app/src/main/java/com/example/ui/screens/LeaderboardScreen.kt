package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LeaderboardEntry
import com.example.model.UserProfile
import com.example.ui.theme.CardNavy
import com.example.ui.theme.CardNavyElevated
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.MidnightBlack
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonLime
import com.example.ui.theme.OutlineNavy
import com.example.ui.theme.RewardGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun LeaderboardScreen(
    entries: List<LeaderboardEntry>,
    selectedTab: String,
    onSelectTab: (String) -> Unit,
    userProfile: UserProfile,
    modifier: Modifier = Modifier
) {
    val tabs = listOf("Global", "Nigeria", "South Africa", "UK", "USA")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DeepNavy),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GLOBAL MUSIC ARENA",
                        color = TextPrimary,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        letterSpacing = 1.sp
                    )
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Trophy",
                        tint = RewardGold,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Weekly competitive rankings. Top players qualify for sponsor prize pools.",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        // Region / Country Filter Tabs
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tabs) { tab ->
                    val isSelected = selectedTab == tab
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) ElectricPurple else CardNavy)
                            .border(1.dp, if (isSelected) ElectricPurple else OutlineNavy, RoundedCornerShape(20.dp))
                            .clickable { onSelectTab(tab) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("tab_$tab")
                    ) {
                        Text(
                            text = tab,
                            color = if (isSelected) Color.White else TextSecondary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Top 3 Podium Card
        if (entries.size >= 3) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardNavyElevated),
                    border = BorderStroke(1.dp, OutlineNavy),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        // 2nd Place
                        PodiumColumn(
                            entry = entries[1],
                            medal = "🥈",
                            medalColor = Color(0xFF94A3B8),
                            height = 100.dp
                        )
                        // 1st Place (Center, Tallest)
                        PodiumColumn(
                            entry = entries[0],
                            medal = "👑",
                            medalColor = RewardGold,
                            height = 125.dp
                        )
                        // 3rd Place
                        PodiumColumn(
                            entry = entries[2],
                            medal = "🥉",
                            medalColor = Color(0xFFD97706),
                            height = 85.dp
                        )
                    }
                }
            }
        }

        // Section Title
        item {
            Text(
                text = "TOP PLAYERS",
                color = TextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
        }

        // Remaining Rankings List
        items(entries.drop(3)) { entry ->
            val isCurrentUser = entry.isUser
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = if (isCurrentUser) CardNavyElevated else CardNavy),
                border = BorderStroke(1.dp, if (isCurrentUser) NeonCyan else OutlineNavy),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("rank_item_${entry.rank}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "#${entry.rank}",
                            color = TextMuted,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp
                        )
                        Text(text = entry.flag, fontSize = 16.sp)
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = entry.username,
                                    color = if (isCurrentUser) NeonCyan else TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                if (isCurrentUser) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(NeonCyan.copy(alpha = 0.2f))
                                            .padding(horizontal = 4.dp, vertical = 1.dp)
                                    ) {
                                        Text(text = "YOU", color = NeonCyan, fontSize = 9.sp, fontWeight = FontWeight.Black)
                                    }
                                }
                            }
                            Text(
                                text = "${entry.country} • Streak: ${entry.streak} 🔥",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Text(
                        text = "${entry.score} pts",
                        color = if (isCurrentUser) NeonCyan else RewardGold,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun PodiumColumn(
    entry: LeaderboardEntry,
    medal: String,
    medalColor: Color,
    height: androidx.compose.ui.unit.Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(text = medal, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = entry.username,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            maxLines = 1
        )
        Text(
            text = "${entry.score} pts",
            color = medalColor,
            fontWeight = FontWeight.Black,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .size(width = 72.dp, height = height)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(medalColor.copy(alpha = 0.4f), CardNavy)
                    )
                )
                .border(1.dp, medalColor.copy(alpha = 0.5f), RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = entry.flag, fontSize = 24.sp)
        }
    }
}
