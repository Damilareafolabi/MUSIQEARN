package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import com.example.model.UserProfile
import com.example.ui.Screen
import com.example.ui.components.GoogleSignInCard
import com.example.ui.components.signOut
import com.example.ui.theme.AcidLime
import com.example.ui.theme.CardOlive
import com.example.ui.theme.CardOliveBorder
import com.example.ui.theme.CardOliveElevated
import com.example.ui.theme.DarkPillBg
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.RewardGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    isMuted: Boolean,
    isAuthenticated: Boolean,
    onToggleMute: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onSetCountry: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = remember { CredentialManager.create(context) }
    val currentUser = Firebase.auth.currentUser

    val countries = listOf(
        Pair("Nigeria", "🇳🇬"),
        Pair("Ghana", "🇬🇭"),
        Pair("South Africa", "🇿🇦"),
        Pair("United Kingdom", "🇬🇧"),
        Pair("United States", "🇺🇸"),
        Pair("Kenya", "🇰🇪"),
        Pair("Canada", "🇨🇦")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cloud Auth Status Card
        if (!isAuthenticated) {
            item {
                GoogleSignInCard(
                    onAuthSuccess = { /* handled by auth listener in viewmodel */ },
                    onAuthError = { /* handled in component */ }
                )
            }
        } else {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardOliveElevated),
                    border = BorderStroke(1.dp, AcidLime.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = AcidLime, modifier = Modifier.size(20.dp))
                            Column {
                                Text(text = "Cloud Account Connected", color = AcidLime, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(text = currentUser?.email ?: "Google Account Active", color = TextMuted, fontSize = 11.sp)
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                signOut(context, credentialManager, onSignOutComplete = {}, scope = coroutineScope)
                            },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.testTag("sign_out_button")
                        ) {
                            Text("Sign Out", color = TextSecondary, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // User Profile Header
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardOlive),
                border = BorderStroke(1.dp, CardOliveBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(AcidLime)
                                    .border(2.dp, CardOliveBorder, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = userProfile.username.take(1),
                                    color = ObsidianBlack,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 24.sp
                                )
                            }
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(
                                        text = userProfile.username,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 17.sp
                                    )
                                    Text(text = userProfile.countryFlag, fontSize = 16.sp)
                                }
                                Text(
                                    text = userProfile.rankTitle,
                                    color = AcidLime,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkPillBg)
                                .border(1.dp, CardOliveBorder, RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(text = "LVL ${userProfile.level}", color = RewardGold, fontWeight = FontWeight.Black, fontSize = 13.sp)
                        }
                    }

                    // Level XP Progress
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Experience Progress", color = TextMuted, fontSize = 11.sp)
                            Text(text = "${userProfile.xp} / ${userProfile.xpNextLevel} XP", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        LinearProgressIndicator(
                            progress = { (userProfile.xp.toFloat() / userProfile.xpNextLevel).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = AcidLime,
                            trackColor = DarkPillBg
                        )
                    }
                }
            }
        }

        // Stats Matrix
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard("GAMES", "${userProfile.totalGamesPlayed}", AcidLime, Modifier.weight(1f))
                StatCard("WINS", "${userProfile.totalWins}", Color(0xFF10B981), Modifier.weight(1f))
                StatCard("STREAK", "${userProfile.currentStreak} 🔥", Color(0xFFF97316), Modifier.weight(1f))
                StatCard("COINS", "${userProfile.musizCoins}", RewardGold, Modifier.weight(1f))
            }
        }

        // Change Country Selector
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardOlive),
                border = BorderStroke(1.dp, CardOliveBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "YOUR REGION & FLAG (FOR LEADERBOARDS)", color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(countries) { (cName, flag) ->
                            val isSelected = userProfile.country == cName
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) AcidLime else DarkPillBg)
                                    .border(1.dp, if (isSelected) AcidLime else CardOliveBorder, RoundedCornerShape(12.dp))
                                    .clickable { onSetCountry(cName, flag) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "$flag $cName",
                                    color = if (isSelected) ObsidianBlack else TextSecondary,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Creator & Sponsor Portal Link
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardOliveElevated),
                border = BorderStroke(1.dp, AcidLime.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(Screen.ARTIST_PORTAL) }
                    .testTag("open_artist_portal_from_profile")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(AcidLime.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, tint = AcidLime, modifier = Modifier.size(20.dp))
                        }
                        Column {
                            Text(text = "Artist & Sponsor Submission Portal", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(text = "Submit original music, manage campaigns & rights", color = TextSecondary, fontSize = 11.sp)
                        }
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                }
            }
        }

        // Audio Sound Control
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardOlive),
                border = BorderStroke(1.dp, CardOliveBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = null,
                            tint = if (isMuted) TextMuted else AcidLime
                        )
                        Column {
                            Text(text = "Synthesizer & Game Audio", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(text = if (isMuted) "Audio is currently muted" else "Dynamic melodies & effects active", color = TextMuted, fontSize = 11.sp)
                        }
                    }
                    Button(
                        onClick = onToggleMute,
                        colors = ButtonDefaults.buttonColors(containerColor = if (isMuted) AcidLime else DarkPillBg),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(text = if (isMuted) "Unmute" else "Mute", fontSize = 12.sp, color = if (isMuted) ObsidianBlack else TextPrimary)
                    }
                }
            }
        }

        // Official Founder & Studio Information (Prompt Requirement)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardOliveElevated),
                border = BorderStroke(1.dp, CardOliveBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "DEVELOPER & STUDIO CREDITS", color = AcidLime, fontWeight = FontWeight.Bold, fontSize = 11.sp, letterSpacing = 1.sp)
                    Text(text = "Founder & Lead Developer:", color = TextMuted, fontSize = 11.sp)
                    Text(text = "Afolabi Oluwadamilare Simeon", color = TextPrimary, fontWeight = FontWeight.Black, fontSize = 16.sp)
                    Text(text = "Studio:", color = TextMuted, fontSize = 11.sp)
                    Text(text = "SimeonJr Studios (Lagos, Nigeria & International)", color = TextSecondary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "Tagline: PLAY MUSIC. CHALLENGE THE WORLD. EARN REWARDS.", color = AcidLime, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:simeonjrpictures123@gmail.com")
                                putExtra(Intent.EXTRA_SUBJECT, "MUSIZEARN Partner / Artist Inquiry")
                            }
                            try { context.startActivity(intent) } catch (_: Exception) {}
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(text = "Contact SimeonJr Studios", color = TextPrimary, fontSize = 12.sp)
                    }
                }
            }
        }

        // Legal, Music Rights & DMCA Notice
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardOlive),
                border = BorderStroke(1.dp, CardOliveBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
                        Text(text = "Music Rights & DMCA Takedown Policy", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Text(
                        text = "MUSIZEARN respects all international intellectual property rights. Game audio utilizes procedurally synthesized melodies, authorized independent artist submissions, and educational trivia metadata. No unauthorized commercial sound recordings are extracted or hosted. For licensing inquiries or DMCA notices, email simeonjrpictures123@gmail.com.",
                        color = TextMuted,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardOlive),
        border = BorderStroke(1.dp, CardOliveBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, color = color, fontWeight = FontWeight.Black, fontSize = 15.sp)
            Text(text = title, color = TextMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }
    }
}
