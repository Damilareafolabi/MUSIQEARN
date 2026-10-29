package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AdminReport
import com.example.model.ArtistSubmission
import com.example.model.SubmissionStatus
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
fun AdminPortalScreen(
    reports: List<AdminReport>,
    submissions: List<ArtistSubmission>,
    onResolveReport: (String) -> Unit,
    onApproveSubmission: (String) -> Unit,
    onRejectSubmission: (String) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf("Reports & Disputes") } // "Reports & Disputes" or "Music Moderation" or "Monetization Audit"

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        // Header
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
                    .testTag("admin_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Admin & Moderation Hub",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin",
                        tint = AcidLime,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = "SimeonJr Studios Governance & Compliance",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
        }

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Reports & Disputes", "Music Moderation", "Revenue Engine").forEach { tab ->
                val isSelected = activeTab == tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) AcidLime else DarkPillBg)
                        .border(1.dp, if (isSelected) AcidLime else CardOliveBorder, RoundedCornerShape(12.dp))
                        .clickable { activeTab = tab }
                        .padding(vertical = 8.dp)
                        .testTag("admin_tab_${tab.take(6)}"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab,
                        color = if (isSelected) ObsidianBlack else TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            when (activeTab) {
                "Reports & Disputes" -> {
                    items(reports) { rep ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CardOlive),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CardOliveBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(RewardGold.copy(alpha = 0.2f))
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(rep.reportCategory, color = RewardGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                    Text(rep.status, color = if (rep.status == "RESOLVED") AcidLime else TextMuted, fontSize = 11.sp)
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Target: ${rep.reportedTarget}",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Reporter: ${rep.reporterName} • ${rep.date}",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = rep.reasonDetail,
                                    color = TextSecondary,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )

                                if (rep.status != "RESOLVED") {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(
                                        onClick = { onResolveReport(rep.id) },
                                        colors = ButtonDefaults.buttonColors(containerColor = DarkPillBg),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = AcidLime, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Mark Resolved", color = TextPrimary, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
                "Music Moderation" -> {
                    items(submissions) { sub ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CardOlive),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CardOliveBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(sub.songTitle, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        Text("${sub.artistName} • ${sub.genre} (${sub.releaseYear})", color = TextMuted, fontSize = 12.sp)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(
                                                when (sub.status) {
                                                    SubmissionStatus.APPROVED_LIVE -> AcidLime.copy(alpha = 0.2f)
                                                    SubmissionStatus.REJECTED -> androidx.compose.ui.graphics.Color.Red.copy(alpha = 0.2f)
                                                    else -> DarkPillBg
                                                }
                                            )
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = sub.status.name,
                                            color = when (sub.status) {
                                                SubmissionStatus.APPROVED_LIVE -> AcidLime
                                                SubmissionStatus.REJECTED -> androidx.compose.ui.graphics.Color.Red
                                                else -> TextSecondary
                                            },
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Preview Link: ${sub.streamingLink}",
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "Rights Declaration: ${if (sub.rightsOwnershipConfirmed) "Full Master Rights Certified ✓" else "Unverified"}",
                                    color = if (sub.rightsOwnershipConfirmed) AcidLime else RewardGold,
                                    fontSize = 11.sp
                                )

                                if (sub.status == SubmissionStatus.PENDING_REVIEW) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = { onApproveSubmission(sub.id) },
                                            colors = ButtonDefaults.buttonColors(containerColor = AcidLime, contentColor = ObsidianBlack),
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Approve", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        }

                                        Button(
                                            onClick = { onRejectSubmission(sub.id) },
                                            colors = ButtonDefaults.buttonColors(containerColor = DarkPillBg),
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Reject", color = TextPrimary, fontSize = 12.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else -> {
                    // Revenue Engine Breakdown
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CardOlive),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CardOliveBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = null, tint = AcidLime, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("MUSIZEARN Revenue Streams", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                                val streams = listOf(
                                    Pair("AdMob Ads", "Interstitial and native ads during non-intrusive game transitions with test ID compliance."),
                                    Pair("Celebrity Booking Commission", "5% - 10% platform facilitation fee per completed contract."),
                                    Pair("Crowdfunding Platform Fee", "8% platform and payment gateway fee on successful Donate For Me disbursements."),
                                    Pair("Brand Sponsored Tournaments", "Direct campaign management fees from telecoms and beverage sponsors."),
                                    Pair("Artist Spotlight & Verification", "Premium analytics and featured placement in Discover feed.")
                                )

                                streams.forEach { (title, desc) ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(DarkPillBg)
                                            .padding(10.dp)
                                    ) {
                                        Column {
                                            Text(title, color = AcidLime, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(desc, color = TextSecondary, fontSize = 11.sp, lineHeight = 15.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
