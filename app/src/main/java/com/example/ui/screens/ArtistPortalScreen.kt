package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ArtistSubmission
import com.example.model.SubmissionStatus
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
fun ArtistPortalScreen(
    submissions: List<ArtistSubmission>,
    onSubmitTrack: (String, String, String, String, String, Boolean) -> Boolean,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var stageName by remember { mutableStateOf("") }
    var songTitle by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("Afro-Fusion") }
    var releaseYear by remember { mutableStateOf("2024") }
    var streamingLink by remember { mutableStateOf("") }
    var rightsConfirmed by remember { mutableStateOf(false) }

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
                        .testTag("artist_portal_back")
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                }

                Text(
                    text = "ARTIST & SPONSOR PORTAL",
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    letterSpacing = 0.5.sp
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ElectricPurple.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = "CREATOR", color = ElectricPurple, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Submissions Submission Form
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardNavyElevated),
                border = BorderStroke(1.dp, OutlineNavy),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(20.dp))
                        Text(text = "Submit Track for Music Challenges", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }

                    Text(
                        text = "Upload your song release to be featured in global trivia games and sponsored challenges. Every submission undergoes human copyright review by SimeonJr Studios.",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )

                    OutlinedTextField(
                        value = stageName,
                        onValueChange = { stageName = it },
                        label = { Text("Artist / Stage Name", fontSize = 12.sp) },
                        singleLine = true,
                        colors = fieldColors(),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("input_stage_name")
                    )

                    OutlinedTextField(
                        value = songTitle,
                        onValueChange = { songTitle = it },
                        label = { Text("Song Title", fontSize = 12.sp) },
                        singleLine = true,
                        colors = fieldColors(),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("input_song_title")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = genre,
                            onValueChange = { genre = it },
                            label = { Text("Genre", fontSize = 12.sp) },
                            singleLine = true,
                            colors = fieldColors(),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f).testTag("input_genre")
                        )
                        OutlinedTextField(
                            value = releaseYear,
                            onValueChange = { releaseYear = it },
                            label = { Text("Year", fontSize = 12.sp) },
                            singleLine = true,
                            colors = fieldColors(),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f).testTag("input_year")
                        )
                    }

                    OutlinedTextField(
                        value = streamingLink,
                        onValueChange = { streamingLink = it },
                        label = { Text("Streaming Link (Audiomack, Spotify, Apple)", fontSize = 12.sp) },
                        singleLine = true,
                        colors = fieldColors(),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("input_stream_link")
                    )

                    // Mandatory Copyright & Rights Declaration
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(MidnightBlack)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = rightsConfirmed,
                            onCheckedChange = { rightsConfirmed = it },
                            colors = CheckboxDefaults.colors(checkedColor = NeonLime, uncheckedColor = TextMuted),
                            modifier = Modifier.testTag("checkbox_rights")
                        )
                        Text(
                            text = "I declare and confirm that I own 100% master & publishing rights or possess written license authorization to submit this music for MUSIZEARN gaming previews.",
                            color = if (rightsConfirmed) NeonLime else TextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 15.sp,
                            modifier = Modifier.clickable { rightsConfirmed = !rightsConfirmed }
                        )
                    }

                    Button(
                        onClick = {
                            if (stageName.isNotBlank() && songTitle.isNotBlank() && rightsConfirmed) {
                                val ok = onSubmitTrack(stageName, songTitle, genre, releaseYear, streamingLink, rightsConfirmed)
                                if (ok) {
                                    stageName = ""
                                    songTitle = ""
                                    streamingLink = ""
                                    rightsConfirmed = false
                                }
                            }
                        },
                        enabled = stageName.isNotBlank() && songTitle.isNotBlank() && rightsConfirmed,
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricPurple),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("submit_song_button")
                    ) {
                        Text(text = "Submit for Admin Review", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }

        // Submissions Queue Status
        item {
            Text(
                text = "SUBMISSIONS & MODERATION STATUS",
                color = TextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
        }

        items(submissions) { sub ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardNavy),
                border = BorderStroke(1.dp, OutlineNavy),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = sub.songTitle, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text(text = "${sub.artistName} • ${sub.genre} (${sub.releaseYear})", color = TextSecondary, fontSize = 12.sp)
                        Text(text = "Submitted: ${sub.submittedDate}", color = TextMuted, fontSize = 10.sp)
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                when (sub.status) {
                                    SubmissionStatus.APPROVED_LIVE -> NeonLime.copy(alpha = 0.2f)
                                    SubmissionStatus.PENDING_REVIEW -> RewardGold.copy(alpha = 0.2f)
                                    SubmissionStatus.REJECTED -> Color(0x33EF4444)
                                }
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = when (sub.status) {
                                    SubmissionStatus.APPROVED_LIVE -> Icons.Default.CheckCircle
                                    SubmissionStatus.PENDING_REVIEW -> Icons.Default.HourglassTop
                                    SubmissionStatus.REJECTED -> Icons.Default.Security
                                },
                                contentDescription = null,
                                tint = when (sub.status) {
                                    SubmissionStatus.APPROVED_LIVE -> NeonLime
                                    SubmissionStatus.PENDING_REVIEW -> RewardGold
                                    SubmissionStatus.REJECTED -> Color(0xFFEF4444)
                                },
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = when (sub.status) {
                                    SubmissionStatus.APPROVED_LIVE -> "LIVE IN GAME"
                                    SubmissionStatus.PENDING_REVIEW -> "IN REVIEW"
                                    SubmissionStatus.REJECTED -> "REJECTED"
                                },
                                color = when (sub.status) {
                                    SubmissionStatus.APPROVED_LIVE -> NeonLime
                                    SubmissionStatus.PENDING_REVIEW -> RewardGold
                                    SubmissionStatus.REJECTED -> Color(0xFFEF4444)
                                },
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = ElectricPurple,
    unfocusedBorderColor = OutlineNavy,
    focusedTextColor = TextPrimary,
    unfocusedTextColor = TextPrimary,
    focusedLabelColor = ElectricPurple,
    unfocusedLabelColor = TextMuted
)
