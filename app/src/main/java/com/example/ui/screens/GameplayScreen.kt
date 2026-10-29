package com.example.ui.screens

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.audio.MusicAudioEngine
import com.example.model.GameMode
import com.example.ui.AnswerState
import com.example.ui.QuizSessionState
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
fun GameplayScreen(
    quizState: QuizSessionState,
    audioEngine: MusicAudioEngine,
    isPlayingAudio: Boolean,
    onOptionSelected: (Int) -> Unit,
    onNextQuestion: () -> Unit,
    onPlayAgain: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    BackHandler { onExit() }

    if (quizState.isFinished) {
        QuizResultsView(
            score = quizState.score,
            mode = quizState.mode,
            onPlayAgain = onPlayAgain,
            onExit = onExit
        )
        return
    }

    val currentQ = quizState.questions.getOrNull(quizState.currentIndex) ?: return

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightBlack),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Navigation & Score Header
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
                        .testTag("exit_game_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }

                Text(
                    text = "${quizState.mode.title} • ${quizState.currentIndex + 1}/${quizState.questions.size}",
                    color = TextSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (quizState.streak > 1) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0x33F97316))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = Color(0xFFF97316),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "${quizState.streak}x",
                                color = Color(0xFFF97316),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(CardNavy)
                            .border(1.dp, OutlineNavy, RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${quizState.score} PTS",
                            color = NeonCyan,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Timer Bar
        item {
            val progress = (quizState.timeRemainingSeconds / 15f).coerceIn(0f, 1f)
            val timerColor = when {
                quizState.timeRemainingSeconds <= 4 -> ErrorRed
                quizState.timeRemainingSeconds <= 8 -> RewardGold
                else -> NeonCyan
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "TIME REMAINING",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "${quizState.timeRemainingSeconds}s",
                        color = timerColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = timerColor,
                    trackColor = CardNavy
                )
            }
        }

        // Music Stage / Audio Visualizer Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardNavyElevated),
                border = BorderStroke(1.dp, OutlineNavy),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("audio_visualizer_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(ElectricPurple.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = currentQ.genre.uppercase(),
                                color = ElectricPurple,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        Text(
                            text = "Release: ${currentQ.year}",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }

                    // Equalizer Waveform
                    SoundwaveVisualizer(
                        isPlaying = isPlayingAudio,
                        modifier = Modifier.fillMaxWidth(),
                        barCount = 24,
                        maxHeight = 56.dp,
                        barWidth = 6.dp
                    )

                    // Audio Playback Indicator & Replay button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = {
                                audioEngine.playMusicSnippet(
                                    riffId = currentQ.snippetRiffId,
                                    title = "${currentQ.title} - ${currentQ.artist}",
                                    durationSec = 10
                                )
                            },
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(CardNavy)
                                .testTag("replay_audio_button")
                        ) {
                            Icon(
                                imageVector = if (isPlayingAudio) Icons.Default.Refresh else Icons.Default.PlayArrow,
                                contentDescription = "Play/Replay melody",
                                tint = NeonCyan,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Text(
                            text = if (isPlayingAudio) "Melody Playing • Listen Closely..." else "Tap to replay audio clip",
                            color = if (isPlayingAudio) NeonLime else TextMuted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if (quizState.mode == GameMode.MISSING_LYRICS && currentQ.lyricsSnippet.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(MidnightBlack)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "\"${currentQ.lyricsSnippet}\"",
                                color = TextPrimary,
                                fontSize = 13.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        // Question Title
        item {
            val promptText = when (quizState.mode) {
                GameMode.NAME_ARTIST -> "Who is the artist behind this track?"
                GameMode.MISSING_LYRICS -> "Which phrase correctly completes the lyrics?"
                GameMode.GENRE_TRIVIA -> "Identify the genre rhythm or song title:"
                else -> "Which song is currently playing?"
            }

            Text(
                text = promptText,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }

        // 4 Options
        items(currentQ.options.size) { index ->
            val optionText = currentQ.options[index]
            val isSelected = quizState.selectedOptionIndex == index
            val isAnswered = quizState.answerState != AnswerState.NONE
            val isCorrectOption = index == currentQ.correctIndex

            val backgroundColor = when {
                !isAnswered && isSelected -> ElectricPurple.copy(alpha = 0.3f)
                isAnswered && isCorrectOption -> NeonLime.copy(alpha = 0.25f)
                isAnswered && isSelected && !isCorrectOption -> ErrorRed.copy(alpha = 0.25f)
                else -> CardNavy
            }

            val borderColor = when {
                isAnswered && isCorrectOption -> NeonLime
                isAnswered && isSelected && !isCorrectOption -> ErrorRed
                isSelected -> ElectricPurple
                else -> OutlineNavy
            }

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                border = BorderStroke(1.5.dp, borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !isAnswered) { onOptionSelected(index) }
                    .testTag("option_button_$index")
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
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MidnightBlack)
                                .border(1.dp, borderColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = ('A' + index).toString(),
                                color = if (isAnswered && isCorrectOption) NeonLime else TextSecondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                        Text(
                            text = optionText,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    }

                    if (isAnswered) {
                        if (isCorrectOption) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Correct",
                                tint = NeonLime,
                                modifier = Modifier.size(20.dp)
                            )
                        } else if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Wrong",
                                tint = ErrorRed,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // Fun Fact / Music Rights Attribution & Next Button
        if (quizState.answerState != AnswerState.NONE) {
            item {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = CardNavy),
                            border = BorderStroke(1.dp, OutlineNavy),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = NeonCyan,
                                    modifier = Modifier.size(18.dp)
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "Did you know?",
                                        color = NeonCyan,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = quizState.funFactRevealed,
                                        color = TextSecondary,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = "Attribution: ${currentQ.rightsAttribution}",
                                        color = TextMuted,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = onNextQuestion,
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricPurple),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("next_question_button")
                        ) {
                            Text(
                                text = if (quizState.currentIndex + 1 >= quizState.questions.size) "See Results" else "Next Track",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuizResultsView(
    score: Int,
    mode: GameMode,
    onPlayAgain: () -> Unit,
    onExit: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CardNavyElevated),
            border = BorderStroke(1.dp, ElectricPurple),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(listOf(ElectricPurple, NeonCyan))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🏆",
                        fontSize = 32.sp
                    )
                }

                Text(
                    text = "CHALLENGE COMPLETE!",
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    letterSpacing = 1.sp
                )

                Text(
                    text = mode.title,
                    color = TextSecondary,
                    fontSize = 13.sp
                )

                // Score Display
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MidnightBlack)
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$score",
                            color = NeonCyan,
                            fontWeight = FontWeight.Black,
                            fontSize = 36.sp
                        )
                        Text(
                            text = "TOTAL POINTS",
                            color = TextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "+${score / 10}", color = RewardGold, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = "MusizCoins", color = TextMuted, fontSize = 11.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "+$score", color = ElectricPurple, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = "XP Gained", color = TextMuted, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "I scored $score points in ${mode.title} on MUSIZEARN! Think you know music better than me? Download MUSIZEARN, challenge me and win airtime rewards!"
                            )
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Score"))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonLime),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("share_score_button")
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = MidnightBlack, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Share Score to WhatsApp", color = MidnightBlack, fontWeight = FontWeight.Bold)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onPlayAgain,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("play_again_button")
                    ) {
                        Text("Play Again", color = TextPrimary)
                    }

                    Button(
                        onClick = onExit,
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricPurple),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("return_home_button")
                    ) {
                        Text("Home", color = Color.White)
                    }
                }
            }
        }
    }
}
