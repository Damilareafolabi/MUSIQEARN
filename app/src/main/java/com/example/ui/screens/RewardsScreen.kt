package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.RewardsData
import com.example.model.RewardCampaign
import com.example.model.RewardType
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
fun RewardsScreen(
    userProfile: UserProfile,
    claimedRewardIds: Set<String>,
    onClaimReward: (RewardCampaign) -> Unit,
    modifier: Modifier = Modifier
) {
    val campaigns = RewardsData.campaigns

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DeepNavy),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title & Balance
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "SPONSORED REWARDS",
                        color = TextPrimary,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Genuine airtime, data & cash funded by verified partners",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardNavy)
                        .border(1.dp, RewardGold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = null, tint = RewardGold, modifier = Modifier.size(16.dp))
                        Text(text = "${userProfile.musizCoins} Coins", color = RewardGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // Transparency & Anti-Fraud Notice Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardNavy),
                border = BorderStroke(1.dp, OutlineNavy),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(20.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Platform Reward & Fairness Notice",
                            color = NeonCyan,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Rewards are funded exclusively by confirmed sponsors, participating record labels, and SimeonJr Studios. Players must qualify with genuine gameplay. Google AdMob operates strictly for server hosting and never pays cash for ad clicks. Server-side fraud checks prevent bot manipulation.",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Section Title
        item {
            Text(
                text = "ACTIVE CAMPAIGNS",
                color = TextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
        }

        // Campaign Cards
        items(campaigns) { campaign ->
            val isClaimed = claimedRewardIds.contains(campaign.id)
            val isEligible = userProfile.xp >= campaign.minScoreToQualify
            val progress = (userProfile.xp.toFloat() / campaign.minScoreToQualify).coerceIn(0f, 1f)

            val badgeColor = when (campaign.rewardType) {
                RewardType.AIRTIME -> NeonLime
                RewardType.DATA_BUNDLE -> NeonCyan
                RewardType.CASH_PRIZE -> RewardGold
                RewardType.MERCH_CONCERT -> ElectricPurple
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardNavyElevated),
                border = BorderStroke(1.dp, if (isClaimed) NeonLime else OutlineNavy),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("campaign_card_${campaign.id}")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Header Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(badgeColor.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = campaign.rewardType.label.uppercase(),
                                    color = badgeColor,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = campaign.title,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Sponsor: ${campaign.sponsorName}",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = campaign.prizeValue,
                                color = RewardGold,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "${campaign.daysRemaining} days left",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Budget & Region Info
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MidnightBlack)
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Remaining: ${campaign.remainingBudget}", color = TextSecondary, fontSize = 11.sp)
                        Text(text = "Region: ${campaign.country}", color = NeonCyan, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }

                    // Qualification Progress
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Required Score: ${campaign.minScoreToQualify} XP",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                            Text(
                                text = if (isEligible) "QUALIFIED" else "${userProfile.xp}/${campaign.minScoreToQualify} XP",
                                color = if (isEligible) NeonLime else TextSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = if (isEligible) NeonLime else ElectricPurple,
                            trackColor = CardNavy
                        )
                    }

                    // Verification & Sponsor Terms
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                        Text(
                            text = campaign.verificationNotice,
                            color = TextMuted,
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    }

                    // Claim Button
                    Button(
                        onClick = { onClaimReward(campaign) },
                        enabled = isEligible && !isClaimed,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isClaimed) CardNavy else RewardGold,
                            disabledContainerColor = CardNavy
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("claim_reward_button_${campaign.id}")
                    ) {
                        if (isClaimed) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = NeonLime, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.size(6.dp))
                            Text(text = "Claim Submitted (Pending Payout)", color = NeonLime, fontWeight = FontWeight.Bold)
                        } else if (!isEligible) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.size(6.dp))
                            Text(text = "Play More To Unlock", color = TextMuted)
                        } else {
                            Text(text = "Claim ${campaign.prizeValue}", color = MidnightBlack, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }
    }
}
