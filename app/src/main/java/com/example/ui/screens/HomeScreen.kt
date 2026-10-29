package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SampleMusicDatabase
import com.example.model.GameMode
import com.example.model.SongQuestion
import com.example.model.UserProfile
import com.example.ui.Screen
import com.example.ui.theme.AcidLime
import com.example.ui.theme.AcidLimeBright
import com.example.ui.theme.CardOlive
import com.example.ui.theme.CardOliveBorder
import com.example.ui.theme.CardOliveElevated
import com.example.ui.theme.DarkPillBg
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.RewardGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun HomeScreen(
    userProfile: UserProfile,
    friendInviteCode: String,
    onStartGame: (GameMode) -> Unit,
    onStartBattle: () -> Unit,
    onPlayTrack: (SongQuestion) -> Unit,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Afrobeats", "Amapiano", "Hip Hop", "Pop", "R&B", "Highlife")

    val allSongs = SampleMusicDatabase.questions
    val filteredSongs = if (selectedCategory == "All") {
        allSongs
    } else {
        allSongs.filter { it.genre.contains(selectedCategory, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack),
        contentPadding = PaddingValues(bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Sonic Journey Hero Card (Screen 1 Reference)
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardOlive),
                border = BorderStroke(1.dp, CardOliveBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("sonic_journey_hero")
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1.1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(AcidLime)
                                )
                                Text(
                                    text = "SONIC GAMING ARENA",
                                    color = AcidLime,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Start Your\nSonic Journey",
                                color = TextPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 22.sp,
                                lineHeight = 26.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Play music games, challenge rivals worldwide & earn airtime rewards.",
                                color = TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            // Play Pill Button (Screen 1 Reference Style)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(DarkPillBg)
                                    .border(1.dp, AcidLime, RoundedCornerShape(24.dp))
                                    .clickable { onStartGame(GameMode.GUESS_SONG) }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                                    .testTag("turn_on_music_hero_button")
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(AcidLime),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = ObsidianBlack,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Text(
                                    text = "Turn on your music",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = null,
                                    tint = AcidLime,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        // 3D Neon Metallic Headphones Image
                        Image(
                            painter = painterResource(id = R.drawable.neon_lime_headphones_1793266828419),
                            contentDescription = "Neon Headphones",
                            modifier = Modifier
                                .size(130.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        // Category Pills (Screen 2 Reference)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Select Categories",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 18.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) AcidLime else DarkPillBg)
                                .border(1.dp, if (isSelected) AcidLime else CardOliveBorder, RoundedCornerShape(20.dp))
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .testTag("cat_$cat")
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) ObsidianBlack else TextSecondary,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // Popular Songs Horizontal Carousel (Screen 2 Reference)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Popular Songs",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "See all >",
                        color = TextMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onNavigate(Screen.DISCOVER) }
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredSongs) { track ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CardOlive),
                            border = BorderStroke(1.dp, CardOliveBorder),
                            modifier = Modifier
                                .width(140.dp)
                                .clickable { onPlayTrack(track) }
                                .testTag("popular_song_${track.id}")
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            Brush.radialGradient(
                                                listOf(CardOliveElevated, ObsidianBlack)
                                            )
                                        )
                                        .border(1.dp, CardOliveBorder, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "🎵", fontSize = 28.sp)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = track.genre.uppercase(),
                                            color = AcidLime,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 9.sp
                                        )
                                    }

                                    // Play icon overlay
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(6.dp)
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(AcidLime),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = "Play",
                                            tint = ObsidianBlack,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = track.title,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    maxLines = 1
                                )
                                Text(
                                    text = track.artist,
                                    color = TextMuted,
                                    fontSize = 11.sp,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }

        // New Collection / Gaming Arena Banners (Screen 2 Reference)
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "New Collection & Arenas",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    item {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = CardOlive),
                            border = BorderStroke(1.dp, CardOliveBorder),
                            modifier = Modifier
                                .width(240.dp)
                                .height(130.dp)
                                .clickable { onStartGame(GameMode.GUESS_SONG) }
                                .testTag("banner_top_songs_global")
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.afrobeats_concert_banner_1793266843407),
                                    contentDescription = "Top Songs Global",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(Color(0xEE090D06), Color(0x66090D06))
                                            )
                                        )
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(14.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = "Top Songs Global", color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 16.sp)
                                        Text(text = "Discover 86 songs", color = TextSecondary, fontSize = 11.sp)
                                    }
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = null,
                                        tint = AcidLime,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = CardOlive),
                            border = BorderStroke(1.dp, CardOliveBorder),
                            modifier = Modifier
                                .width(240.dp)
                                .height(130.dp)
                                .clickable { onStartBattle() }
                                .testTag("banner_1v1_battle_arena")
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.musizearn_hero_banner_1793264514488),
                                    contentDescription = "1v1 Music Battle Arena",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(Color(0xEE090D06), Color(0x66090D06))
                                            )
                                        )
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(14.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = "1v1 Battle Arena", color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 16.sp)
                                        Text(text = "Challenge the World", color = AcidLime, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = null,
                                        tint = AcidLime,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // MUSIZEARN Platform Ecosystem Hubs
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "EXPLORE MUSIZEARN ECOSYSTEM",
                    color = TextMuted,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Celebrity Fan Clubs Card
                    Card(
                        onClick = { onNavigate(Screen.FAN_CLUBS) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardOlive),
                        border = BorderStroke(1.dp, CardOliveBorder),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hub_card_fan_clubs")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(DarkPillBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Campaign,
                                    contentDescription = null,
                                    tint = AcidLime,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Fan Clubs & Chat",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Burna, Tems & Asake drops",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Donate for Me Card
                    Card(
                        onClick = { onNavigate(Screen.CROWDFUNDING) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardOlive),
                        border = BorderStroke(1.dp, CardOliveBorder),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hub_card_crowdfunding")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(DarkPillBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = RewardGold,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Donate for Me",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Support indie recordings",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Book a Celebrity Card
                    Card(
                        onClick = { onNavigate(Screen.BOOKINGS) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardOlive),
                        border = BorderStroke(1.dp, CardOliveBorder),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hub_card_bookings")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(DarkPillBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = AcidLime,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Book a Celebrity",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Concerts & festival talent",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Admin & Moderation Hub
                    Card(
                        onClick = { onNavigate(Screen.ADMIN_PORTAL) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CardOlive),
                        border = BorderStroke(1.dp, CardOliveBorder),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("hub_card_admin")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(DarkPillBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Admin & Rights",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Disputes & verification",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // Daily Music Challenge Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardOliveElevated),
                border = BorderStroke(1.dp, AcidLime.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .testTag("daily_challenge_card")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x33F97316)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = Color(0xFFF97316),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(text = "Daily Music Challenge", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(text = "Streak: ${userProfile.currentStreak} Days • +50 XP", color = TextSecondary, fontSize = 12.sp)
                            }
                        }

                        Button(
                            onClick = { onStartGame(GameMode.DAILY_CHALLENGE) },
                            colors = ButtonDefaults.buttonColors(containerColor = AcidLime),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(text = "Play", color = ObsidianBlack, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }

        // Game Modes Grid / List
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "ALL GAME MODES",
                    color = TextMuted,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )

                GameModePill("Guess the Song", "Listen to the melody & identify the track", Icons.Default.MusicNote) {
                    onStartGame(GameMode.GUESS_SONG)
                }
                GameModePill("Name the Artist", "Identify the hitmaker behind the groove", Icons.Default.Mic) {
                    onStartGame(GameMode.NAME_ARTIST)
                }
                GameModePill("1v1 Music Battle", "Head-to-head showdown with live scoring", Icons.Default.SportsEsports) {
                    onStartBattle()
                }
                GameModePill("Missing Lyrics", "Complete missing lines from classic hits", Icons.Default.GraphicEq) {
                    onStartGame(GameMode.MISSING_LYRICS)
                }
            }
        }

        // Challenge a Friend WhatsApp Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardOlive),
                border = BorderStroke(1.dp, CardOliveBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "CHALLENGE A FRIEND", color = TextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = friendInviteCode, color = AcidLime, fontWeight = FontWeight.Black, fontSize = 16.sp, letterSpacing = 1.sp)
                        Button(
                            onClick = {
                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "Hey! I challenge you to a music battle on MUSIZEARN! Join my room using code: $friendInviteCode"
                                    )
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share Challenge"))
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AcidLime),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = ObsidianBlack, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Share", color = ObsidianBlack, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GameModePill(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardOlive),
        border = BorderStroke(1.dp, CardOliveBorder),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(CardOliveElevated)
                        .border(1.dp, CardOliveBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = AcidLime, modifier = Modifier.size(20.dp))
                }
                Column {
                    Text(text = title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = subtitle, color = TextSecondary, fontSize = 11.sp)
                }
            }
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted, modifier = Modifier.size(20.dp))
        }
    }
}
