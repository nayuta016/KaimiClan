package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tag
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
import com.example.R
import com.example.data.models.VideoModel
import com.example.ui.components.VerifiedBadge
import com.example.ui.theme.TikTokBlack
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkSurface
import com.example.ui.theme.TikTokGray
import com.example.ui.theme.TikTokPink

/**
 * Halaman Search / Discover Kaimi-Clan
 */
@Composable
fun DiscoverScreen(
    videos: List<VideoModel>
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredVideos = if (searchQuery.isBlank()) videos else videos.filter {
        it.caption.contains(searchQuery, ignoreCase = true) ||
                it.creatorUsername.contains(searchQuery, ignoreCase = true) ||
                it.soundTitle.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TikTokBlack)
            .statusBarsPadding()
            .padding(top = 8.dp)
            .testTag("discover_screen")
    ) {
        // Search Input Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Cari pengguna, hashtag, musik...", color = TikTokGray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Cari", tint = TikTokGray) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("input_search"),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = TikTokCardBg,
                    unfocusedContainerColor = TikTokCardBg,
                    focusedBorderColor = TikTokPink,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                singleLine = true
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Trending Hashtags Carousel
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Hashtag Trending 🔥",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    val trendingTags = listOf("KaimiClan", "DanceChallenge", "CoffeeVibes", "GamingIndo", "TikTokViral")

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(trendingTags) { tag ->
                            Surface(
                                color = TikTokCardBg,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.clickable { searchQuery = tag }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Tag, contentDescription = null, tint = TikTokCyan, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = tag, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }

            // Musik Trending
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Suara & Musik Viral 🎵",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val trendingSounds = listOf(
                        "Kaimi Clan Beat Original - DJ Kaimi Remix",
                        "Chill Morning Jazz Lounge - Coffee Beats",
                        "Phonk Gaming Anthem 2026 - Cyber Audio"
                    )

                    trendingSounds.forEach { sound ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(TikTokDarkSurface)
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(TikTokPink.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.MusicNote, contentDescription = null, tint = TikTokPink)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = sound, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(text = "Dipakai di 12.4K video", fontSize = 11.sp, color = TikTokGray)
                            }
                        }
                    }
                }
            }

            // Grid Hasil Video
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Text(
                        text = if (searchQuery.isBlank()) "Rekomendasi Video" else "Hasil Pencarian \"$searchQuery\"",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (filteredVideos.isEmpty()) {
                        Text(
                            text = "Tidak ada video ditemukan.",
                            color = TikTokGray,
                            fontSize = 14.sp
                        )
                    } else {
                        // Grid 2 Kolom
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            filteredVideos.chunked(2).forEach { rowVideos ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    rowVideos.forEach { video ->
                                        Box(modifier = Modifier.weight(1f)) {
                                            DiscoverVideoCard(video = video)
                                        }
                                    }
                                    if (rowVideos.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
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

@Composable
fun DiscoverVideoCard(video: VideoModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = TikTokCardBg)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = video.coverImageRes),
                contentDescription = video.caption,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(8.dp)
            ) {
                Text(
                    text = video.caption,
                    color = Color.White,
                    fontSize = 11.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "@${video.creatorUsername}",
                        color = TikTokGray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (video.isCreatorVerified) {
                        Spacer(modifier = Modifier.width(4.dp))
                        VerifiedBadge(size = 11.dp)
                    }
                }
            }
        }
    }
}
