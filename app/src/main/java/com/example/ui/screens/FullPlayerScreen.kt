package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.GameMode
import com.example.model.SongQuestion
import com.example.ui.components.SoundwaveVisualizer
import com.example.ui.theme.AcidLime
import com.example.ui.theme.AcidLimeBright
import com.example.ui.theme.AcidLimeDark
import com.example.ui.theme.AcidLimeGlow
import com.example.ui.theme.CardOlive
import com.example.ui.theme.CardOliveBorder
import com.example.ui.theme.CardOliveElevated
import com.example.ui.theme.DarkPillBg
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.OutlineDark
import com.example.ui.theme.RewardGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun FullPlayerScreen(
    currentTrack: SongQuestion,
    isPlaying: Boolean,
    progress: Float,
    onTogglePlay: () -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    onStartQuizForTrack: (SongQuestion) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onExit() }

    var isFavorite by remember { mutableStateOf(false) }
    var isShuffle by remember { mutableStateOf(false) }
    var isRepeat by remember { mutableStateOf(true) }

    // Vinyl rotation animation when playing
    val infiniteTransition = rememberInfiniteTransition(label = "vinyl_rotate")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        // Upper Backdrop: Concert Atmosphere
        Image(
            painter = painterResource(id = R.drawable.afrobeats_concert_banner_1793266843407),
            contentDescription = "Artist Stage Backdrop",
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp),
            contentScale = ContentScale.Crop
        )

        // Dark Vignette Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x99090D06),
                            Color(0xEE090D06),
                            ObsidianBlack,
                            ObsidianBlack
                        ),
                        startY = 0f,
                        endY = 1000f
                    )
                )
        )

        // Screen Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header: Back, Category / NOW PLAYING, Options
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
                        .background(DarkPillBg)
                        .border(1.dp, CardOliveBorder, CircleShape)
                        .testTag("player_back_button")
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary, modifier = Modifier.size(20.dp))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "NOW PLAYING",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = AcidLime,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = currentTrack.genre.uppercase(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextMuted
                    )
                }

                IconButton(
                    onClick = { /* options */ },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(DarkPillBg)
                        .border(1.dp, CardOliveBorder, CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More", tint = TextPrimary, modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Center Circular Vinyl / Jog-Wheel Sound Controller (Inspired by Reference Image Screen 3)
            Box(
                modifier = Modifier
                    .size(280.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outer Subtle Groove Ring
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    Color(0x33C6F432),
                                    Color(0x111E2B13),
                                    Color(0xFF0D1409)
                                )
                            )
                        )
                        .border(1.dp, CardOliveBorder, CircleShape)
                        .rotate(if (isPlaying) rotation else 0f)
                )

                // Middle Concentric Ring with Track Degree Markers
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(CircleShape)
                        .background(CardOlive)
                        .border(2.dp, if (isPlaying) AcidLime.copy(alpha = 0.5f) else CardOliveBorder, CircleShape)
                )

                // Concentric Glowing Jog-Wheel Center
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    CardOliveElevated,
                                    Color(0xFF131D0E),
                                    ObsidianBlack
                                )
                            )
                        )
                        .border(3.dp, AcidLime, CircleShape)
                        .clickable { onTogglePlay() }
                        .testTag("jog_wheel_center_play"),
                    contentAlignment = Alignment.Center
                ) {
                    // Pulsing Glow behind play button
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(if (isPlaying) AcidLimeGlow else Color.Transparent)
                    )

                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = AcidLimeBright,
                        modifier = Modifier.size(46.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Track Title, Artist, & Favorite Heart
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = currentTrack.title,
                        color = TextPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        maxLines = 1
                    )
                    Text(
                        text = "${currentTrack.artist} • ${currentTrack.year}",
                        color = TextSecondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(DarkPillBg)
                        .border(1.dp, CardOliveBorder, CircleShape)
                        .testTag("favorite_button")
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) AcidLime else TextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Scrubber Slider & Progress Time
            Column(modifier = Modifier.fillMaxWidth()) {
                val currentSec = (progress * 198).toInt()
                val totalSec = 198
                val elapsedStr = String.format("%d:%02d", currentSec / 60, currentSec % 60)
                val totalStr = String.format("%d:%02d", totalSec / 60, totalSec % 60)

                Slider(
                    value = progress,
                    onValueChange = { /* scrub */ },
                    colors = SliderDefaults.colors(
                        thumbColor = AcidLimeBright,
                        activeTrackColor = AcidLime,
                        inactiveTrackColor = CardOliveElevated
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = elapsedStr, color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = totalStr, color = TextMuted, fontSize = 12.sp)
                }
            }

            // Playback Controls Row: Shuffle, Prev, Play/Pause, Next, Repeat
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { isShuffle = !isShuffle }) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = "Shuffle",
                        tint = if (isShuffle) AcidLime else TextMuted,
                        modifier = Modifier.size(22.dp)
                    )
                }

                IconButton(onClick = onPrev) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous",
                        tint = TextPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(AcidLime)
                        .clickable { onTogglePlay() }
                        .testTag("player_play_pause_bottom"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = ObsidianBlack,
                        modifier = Modifier.size(30.dp)
                    )
                }

                IconButton(onClick = onNext) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        tint = TextPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                IconButton(onClick = { isRepeat = !isRepeat }) {
                    Icon(
                        imageVector = Icons.Default.Repeat,
                        contentDescription = "Repeat",
                        tint = if (isRepeat) AcidLime else TextMuted,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Sonic Gaming Integration: "Play Music Quiz on this Song"
            Button(
                onClick = { onStartQuizForTrack(currentTrack) },
                colors = ButtonDefaults.buttonColors(containerColor = CardOliveElevated),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AcidLime),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("play_quiz_for_track_button")
            ) {
                Icon(
                    imageVector = Icons.Default.SportsEsports,
                    contentDescription = null,
                    tint = AcidLime,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Play Song Quiz & Win XP",
                    color = AcidLime,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
