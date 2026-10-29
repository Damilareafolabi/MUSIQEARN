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
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BookingRequest
import com.example.model.BookingStatus
import com.example.model.CelebrityBookingProfile
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
fun CelebrityBookingsScreen(
    profiles: List<CelebrityBookingProfile>,
    requests: List<BookingRequest>,
    onSubmitBookingRequest: (BookingRequest) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("Directory") } // "Directory" or "My Enquiries"
    var selectedProfileForBooking by remember { mutableStateOf<CelebrityBookingProfile?>(null) }

    // Enquiry form states
    var eventType by remember { mutableStateOf("Festival Headline") }
    var eventDate by remember { mutableStateOf("Nov 28, 2026") }
    var venueCity by remember { mutableStateOf("Lagos, Nigeria") }
    var audienceCount by remember { mutableStateOf("5,000") }
    var budgetOffer by remember { mutableStateOf("\$25,000") }
    var organizerName by remember { mutableStateOf("Star Events Ltd") }
    var organizerEmail by remember { mutableStateOf("booking@starevents.com") }
    var specialNotes by remember { mutableStateOf("Full live band setup with soundcheck 4 hours prior.") }

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
                    .testTag("bookings_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Book a Celebrity",
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Verified managers, contracts, quotations & status",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
        }

        // Tab Selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf("Directory", "My Enquiries (${requests.size})").forEach { tab ->
                val isSelected = (tab.startsWith("Directory") && selectedTab == "Directory") ||
                        (tab.startsWith("My Enquiries") && selectedTab == "My Enquiries")
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) AcidLime else DarkPillBg)
                        .border(
                            1.dp,
                            if (isSelected) AcidLime else CardOliveBorder,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            selectedTab = if (tab.startsWith("Directory")) "Directory" else "My Enquiries"
                        }
                        .padding(vertical = 10.dp)
                        .testTag("bookings_tab_${tab.take(5)}"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab,
                        color = if (isSelected) ObsidianBlack else TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        if (selectedTab == "Directory") {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 10.dp, bottom = 120.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    // Marketplace info notice
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkPillBg),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardOliveBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = AcidLime,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "All talent profiles are verified with certified talent agencies. Requests undergo manager quotation before confirmation. Disclosed 5% platform escrow fee.",
                                color = TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }

                items(profiles) { profile ->
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
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = profile.celebrityName,
                                            color = TextPrimary,
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Verified Talent",
                                            tint = AcidLime,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Text(
                                        text = "${profile.specialty} • ${profile.country} ${profile.flag}",
                                        color = TextMuted,
                                        fontSize = 12.sp
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = RewardGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "${profile.rating}",
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(DarkPillBg)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Agency: ${profile.managerContact}",
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                    Text(
                                        text = "${profile.completedBookings} Completed",
                                        color = AcidLime,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Estimate Starting Fee: ${profile.startingFeeEstimate}",
                                color = RewardGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { selectedProfileForBooking = profile },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AcidLime,
                                    contentColor = ObsidianBlack
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("book_artist_${profile.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.BusinessCenter,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Request Booking Quotation",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // My Enquiries / Booking Pipeline
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 10.dp, bottom = 120.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (requests.isEmpty()) {
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = CardOlive),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No Booking Requests Yet",
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Browse the verified directory to submit event proposals to celebrity managers.",
                                    color = TextMuted,
                                    fontSize = 12.sp,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    items(requests) { req ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
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
                                    Text(
                                        text = req.celebrityName,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                when (req.status) {
                                                    BookingStatus.CONFIRMED -> AcidLime.copy(alpha = 0.2f)
                                                    BookingStatus.IN_REVIEW -> RewardGold.copy(alpha = 0.2f)
                                                    else -> DarkPillBg
                                                }
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = req.status.label,
                                            color = when (req.status) {
                                                BookingStatus.CONFIRMED -> AcidLime
                                                BookingStatus.IN_REVIEW -> RewardGold
                                                else -> TextSecondary
                                            },
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "${req.eventType} • ${req.venueCity}",
                                    color = TextSecondary,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "Target Date: ${req.eventDate} | Budget Offer: ${req.budgetOffer}",
                                    color = TextMuted,
                                    fontSize = 12.sp
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(DarkPillBg)
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = "Notes: ${req.specialRequirements}",
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Booking Submission Modal
    selectedProfileForBooking?.let { profile ->
        androidx.compose.ui.window.Dialog(onDismissRequest = { selectedProfileForBooking = null }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardOlive),
                border = androidx.compose.foundation.BorderStroke(1.dp, AcidLime),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(20.dp)) {
                    item {
                        Text(
                            text = "Book ${profile.celebrityName}",
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Agency: ${profile.managerContact}",
                            color = TextMuted,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = organizerName,
                            onValueChange = { organizerName = it },
                            label = { Text("Organizer / Company Name") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AcidLime,
                                unfocusedBorderColor = CardOliveBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = eventType,
                            onValueChange = { eventType = it },
                            label = { Text("Event Type (Concert, Festival, Club)") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AcidLime,
                                unfocusedBorderColor = CardOliveBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = eventDate,
                            onValueChange = { eventDate = it },
                            label = { Text("Target Event Date") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AcidLime,
                                unfocusedBorderColor = CardOliveBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = venueCity,
                            onValueChange = { venueCity = it },
                            label = { Text("Venue & City") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AcidLime,
                                unfocusedBorderColor = CardOliveBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = budgetOffer,
                            onValueChange = { budgetOffer = it },
                            label = { Text("Budget Offer (USD)") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AcidLime,
                                unfocusedBorderColor = CardOliveBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Button(
                                onClick = { selectedProfileForBooking = null },
                                colors = ButtonDefaults.buttonColors(containerColor = DarkPillBg),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Cancel", color = TextPrimary)
                            }
                            Button(
                                onClick = {
                                    val newReq = BookingRequest(
                                        id = "req-${System.currentTimeMillis()}",
                                        celebrityName = profile.celebrityName,
                                        organizerName = organizerName,
                                        organizerEmail = organizerEmail,
                                        eventType = eventType,
                                        eventDate = eventDate,
                                        venueCity = venueCity,
                                        expectedAudience = audienceCount,
                                        budgetOffer = budgetOffer,
                                        specialRequirements = specialNotes,
                                        status = BookingStatus.SUBMITTED,
                                        dateSubmitted = "Today"
                                    )
                                    onSubmitBookingRequest(newReq)
                                    selectedProfileForBooking = null
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AcidLime,
                                    contentColor = ObsidianBlack
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Send Enquiry", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
