package com.example.ui.screens

import android.content.Intent
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BattleState
import com.example.model.BattleStatus
import com.example.model.UserProfile
import com.example.ui.components.SoundwaveVisualizer
import com.example.ui.theme.CardNavy
import com.example.ui.theme.CardNavyElevated
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.MidnightBlack
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonLime
import com.example.ui.theme.OutlineNavy
import com.example.ui.theme.RewardGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun BattleScreen(
    battleState: BattleState,
    userProfile: UserProfile,
    friendCode: String,
    isPlayingAudio: Boolean,
    onAnswerOption: (Int) -> Unit,
    onGenerateCode: () -> Unit,
    onStartBattle: (String, String) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var joinCodeInput by remember { mutableStateOf("") }
    BackHandler { onExit() }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onExit,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(CardNavy)
                        .testTag("battle_back_button")
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                }

                Text(
                    text = "1v1 MUSIC BATTLE",
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    letterSpacing = 1.sp
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(NeonLime.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = "LIVE PVP", color = NeonLime, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Versus Scoreboard
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Player 1 (User)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(ElectricPurple, NeonCyan))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "YOU", color = Color.White, fontWeight = FontWeight.Black, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = userProfile.username, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1)
                        Text(text = userProfile.countryFlag, fontSize = 14.sp)
                        Text(text = "${battleState.playerScore} pts", color = NeonCyan, fontWeight = FontWeight.Black, fontSize = 18.sp)
                    }

                    // VS Emblem & Round
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x33EC4899))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(text = "ROUND ${battleState.round}/${battleState.totalRounds}", color = Color(0xFFF43F5E), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                        Text(text = "VS", color = TextMuted, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    }

                    // Opponent
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(Color(0xFFF43F5E), RewardGold))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "RIVAL", color = Color.White, fontWeight = FontWeight.Black, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = battleState.opponentName, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1)
                        Text(text = battleState.opponentCountry, fontSize = 12.sp, color = TextMuted)
                        Text(text = "${battleState.opponentScore} pts", color = RewardGold, fontWeight = FontWeight.Black, fontSize = 18.sp)
                    }
                }
            }
        }

        // Active Battle State or Match Completed
        if (battleState.status == BattleStatus.MATCH_COMPLETE) {
            item {
                val won = battleState.playerScore >= battleState.opponentScore
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = if (won) CardNavyElevated else CardNavy),
                    border = BorderStroke(1.dp, if (won) NeonLime else OutlineNavy),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = if (won) "🏆 VICTORY!" else "DEFEAT", color = if (won) NeonLime else ErrorRed, fontWeight = FontWeight.Black, fontSize = 24.sp)
                        Text(
                            text = if (won) "You out-streamed and out-guessed your rival!" else "Close clash! Practice your music knowledge and rematch.",
                            color = TextSecondary,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { onStartBattle("AfroGroove_${(10..99).random()}", "🇿🇦 South Africa") },
                                colors = ButtonDefaults.buttonColors(containerColor = ElectricPurple),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f).height(44.dp).testTag("rematch_button")
                            ) {
                                Text("New Match", fontWeight = FontWeight.Bold)
                            }
                            OutlinedButton(
                                onClick = onExit,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f).height(44.dp)
                            ) {
                                Text("Exit", color = TextPrimary)
                            }
                        }
                    }
                }
            }
        } else {
            // Live Question Options
            battleState.currentQuestion?.let { q ->
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Identify the song preview:", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            SoundwaveVisualizer(isPlaying = isPlayingAudio, barCount = 12, maxHeight = 30.dp, barWidth = 4.dp)
                        }

                        q.options.forEachIndexed { idx, opt ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = CardNavy),
                                border = BorderStroke(1.dp, OutlineNavy),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onAnswerOption(idx) }
                                    .testTag("battle_option_$idx")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clip(CircleShape)
                                            .background(MidnightBlack)
                                            .border(1.dp, OutlineNavy, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = ('A' + idx).toString(), color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Text(text = opt, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Friend Challenge Code Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardNavy),
                border = BorderStroke(1.dp, OutlineNavy),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "CHALLENGE A FRIEND", color = TextSecondary, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Your Shareable Code:", color = TextMuted, fontSize = 11.sp)
                            Text(text = friendCode, color = NeonCyan, fontWeight = FontWeight.Black, fontSize = 16.sp, letterSpacing = 1.sp)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            IconButton(onClick = onGenerateCode, modifier = Modifier.size(36.dp).clip(CircleShape).background(MidnightBlack)) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Regenerate Code", tint = TextPrimary, modifier = Modifier.size(18.dp))
                            }
                            IconButton(
                                onClick = {
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(
                                            Intent.EXTRA_TEXT,
                                            "Battle me on MUSIZEARN! Join with my challenge code: $friendCode"
                                        )
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(sendIntent, "Send Code"))
                                },
                                modifier = Modifier.size(36.dp).clip(CircleShape).background(ElectricPurple)
                            ) {
                                Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = Color.White, modifier = Modifier.size(18.dp))
                            }
                        }
                    }

                    // Input friend code
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = joinCodeInput,
                            onValueChange = { joinCodeInput = it.uppercase() },
                            placeholder = { Text("Enter friend's code", color = TextMuted, fontSize = 12.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricPurple,
                                unfocusedBorderColor = OutlineNavy,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f).testTag("join_code_input")
                        )

                        Button(
                            onClick = {
                                if (joinCodeInput.isNotBlank()) {
                                    onStartBattle("Challenger_${joinCodeInput.takeLast(4)}", "🌍 Global")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NeonLime),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.height(48.dp).testTag("join_battle_button")
                        ) {
                            Text("Join", color = MidnightBlack, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
