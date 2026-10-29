package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.model.CrowdfundingCampaign
import com.example.ui.theme.AcidLime
import com.example.ui.theme.CardOlive
import com.example.ui.theme.CardOliveBorder
import com.example.ui.theme.DarkPillBg
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.RewardGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CrowdfundingScreen(
    campaigns: List<CrowdfundingCampaign>,
    onDonate: (String, Double) -> Unit,
    onCreateCampaign: (String, String, String, Double) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDonateDialogForCampaign by remember { mutableStateOf<CrowdfundingCampaign?>(null) }
    var showCreateDialog by remember { mutableStateOf(false) }

    // Create form states
    var newTitle by remember { mutableStateOf("") }
    var newCategory by remember { mutableStateOf("Recording & Production") }
    var newStory by remember { mutableStateOf("") }
    var newTargetAmount by remember { mutableStateOf("1500") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onExit,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(DarkPillBg)
                    .testTag("crowdfunding_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Donate for Me — Artist Support",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Fund independent recording, equipment & visuals",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
            IconButton(
                onClick = { showCreateDialog = true },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(AcidLime)
                    .testTag("launch_campaign_button")
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Create Campaign",
                    tint = ObsidianBlack
                )
            }
        }

        // Transparency & Security Notice
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = DarkPillBg),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardOliveBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = AcidLime,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Verified Crowdfunding: 100% transparent tracking. All project owners undergo identity & rights verification before receiving disbursements.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }

        // Campaign Feed
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(campaigns) { campaign ->
                val progress = (campaign.raisedAmountUsd / campaign.targetAmountUsd).toFloat().coerceIn(0f, 1f)
                val percentInt = (progress * 100).toInt()

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardOlive),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardOliveBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkPillBg)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = campaign.purposeCategory,
                                    color = AcidLime,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "${campaign.daysRemaining} days left",
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = campaign.title,
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "By ${campaign.artistName} • ${campaign.artistCountry} ${campaign.artistFlag}",
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            if (campaign.verifiedCreator) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verified Artist",
                                    tint = AcidLime,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = campaign.story,
                            color = TextSecondary,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        // Progress Bar
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = AcidLime,
                            trackColor = DarkPillBg,
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "\$${campaign.raisedAmountUsd.toInt()} raised",
                                    color = AcidLime,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "Goal: \$${campaign.targetAmountUsd.toInt()} ($percentInt%)",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "${campaign.supportersCount} Supporters",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Fan community",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Rewards / Perks
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkPillBg)
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Donor Perk:",
                                    color = RewardGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = campaign.perksDescription,
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { showDonateDialogForCampaign = campaign },
                            colors = ButtonDefaults.buttonColors(containerColor = AcidLime, contentColor = ObsidianBlack),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("support_campaign_${campaign.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolunteerActivism,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Support Artist Fund",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }

    // Support Modal Dialog
    showDonateDialogForCampaign?.let { campaign ->
        androidx.compose.ui.window.Dialog(onDismissRequest = { showDonateDialogForCampaign = null }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardOlive),
                border = androidx.compose.foundation.BorderStroke(1.dp, AcidLime),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Support ${campaign.artistName}",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = campaign.title,
                        color = TextMuted,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Select Support Amount",
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(5.0, 15.0, 50.0, 100.0).forEach { amount ->
                            Button(
                                onClick = {
                                    onDonate(campaign.id, amount)
                                    showDonateDialogForCampaign = null
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = DarkPillBg),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CardOliveBorder),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("\$${amount.toInt()}", color = AcidLime, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Transparent fee notice: 92% of contributions go directly to verified project studio expenses; 8% covers gateway transaction & third-party audit fees.",
                        color = TextMuted,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showDonateDialogForCampaign = null },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkPillBg),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Close", color = TextPrimary)
                    }
                }
            }
        }
    }

    // Launch Campaign Modal
    if (showCreateDialog) {
        androidx.compose.ui.window.Dialog(onDismissRequest = { showCreateDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardOlive),
                border = androidx.compose.foundation.BorderStroke(1.dp, AcidLime),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Launch 'Donate for Me' Campaign",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Must be verified artist or upcoming creator",
                        color = TextMuted,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Campaign Title") },
                        placeholder = { Text("e.g. Mixing & Vinyl for Debut EP") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AcidLime,
                            unfocusedBorderColor = CardOliveBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = newStory,
                        onValueChange = { newStory = it },
                        label = { Text("Story & Production Breakdown") },
                        placeholder = { Text("Describe studio costs and milestones...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AcidLime,
                            unfocusedBorderColor = CardOliveBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = newTargetAmount,
                        onValueChange = { newTargetAmount = it },
                        label = { Text("Target Budget (USD)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AcidLime,
                            unfocusedBorderColor = CardOliveBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { showCreateDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkPillBg),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = TextPrimary)
                        }

                        Button(
                            onClick = {
                                val target = newTargetAmount.toDoubleOrNull() ?: 1000.0
                                onCreateCampaign(newTitle, newCategory, newStory, target)
                                showCreateDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AcidLime, contentColor = ObsidianBlack),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Submit", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
