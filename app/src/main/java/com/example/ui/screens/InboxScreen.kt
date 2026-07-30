package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.models.NotificationModel
import com.example.data.models.NotificationType
import com.example.ui.components.VerifiedBadge
import com.example.ui.theme.TikTokBlack
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkSurface
import com.example.ui.theme.TikTokGray
import com.example.ui.theme.TikTokPink

/**
 * Halaman Notifikasi & Kotak Masuk (Inbox) Kaimi-Clan
 */
@Composable
fun InboxScreen(
    notifications: List<NotificationModel>
) {
    var selectedFilter by remember { mutableStateOf("Semua") }
    val filters = listOf("Semua", "Disukai", "Komentar", "Pengikut")

    val filteredList = notifications.filter { notif ->
        when (selectedFilter) {
            "Disukai" -> notif.type == NotificationType.LIKE
            "Komentar" -> notif.type == NotificationType.COMMENT
            "Pengikut" -> notif.type == NotificationType.FOLLOW
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TikTokBlack)
            .statusBarsPadding()
            .padding(top = 8.dp)
            .testTag("inbox_screen")
    ) {
        Text(
            text = "Kotak Masuk & Aktivitas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(filters) { filter ->
                val isSelected = selectedFilter == filter
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFilter = filter },
                    label = {
                        Text(
                            text = filter,
                            color = if (isSelected) Color.White else TikTokGray,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TikTokPink,
                        containerColor = TikTokCardBg
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        // Daftar Notifikasi
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if (filteredList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Belum ada notifikasi di kategori ini.", color = TikTokGray)
                    }
                }
            } else {
                items(filteredList, key = { it.id }) { notif ->
                    NotificationRow(notif = notif)
                }
            }
        }
    }
}

@Composable
fun NotificationRow(notif: NotificationModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TikTokDarkSurface)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                model = notif.userAvatarUrl,
                contentDescription = notif.username,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(TikTokCardBg),
                contentScale = ContentScale.Crop
            )

            // Small Notification Type Icon Badge
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(
                        when (notif.type) {
                            NotificationType.LIKE -> TikTokPink
                            NotificationType.COMMENT -> TikTokCyan
                            NotificationType.FOLLOW -> Color(0xFF4CAF50)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (notif.type) {
                        NotificationType.LIKE -> Icons.Default.Favorite
                        NotificationType.COMMENT -> Icons.Default.ModeComment
                        NotificationType.FOLLOW -> Icons.Default.PersonAdd
                    },
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = notif.username,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
                if (notif.isVerified) {
                    Spacer(modifier = Modifier.width(4.dp))
                    VerifiedBadge(size = 13.dp)
                }
            }
            Text(
                text = notif.message,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.9f),
                maxLines = 2
            )
            Text(
                text = notif.timeAgo,
                fontSize = 11.sp,
                color = TikTokGray,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        if (notif.videoThumbnailRes != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = notif.videoThumbnailRes),
                contentDescription = "Thumbnail Video",
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )
        } else if (notif.type == NotificationType.FOLLOW) {
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = TikTokPink),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("Ikuti Balik", fontSize = 11.sp, color = Color.White)
            }
        }
    }
}
