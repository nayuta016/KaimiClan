package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
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
import com.example.data.models.UserModel
import com.example.data.models.VideoModel
import com.example.ui.components.VerifiedBadge
import com.example.ui.theme.TikTokBlack
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkSurface
import com.example.ui.theme.TikTokGray
import com.example.ui.theme.TikTokPink

/**
 * Halaman Profil Pengguna / Creator (Profile Screen) Kaimi-Clan
 */
@Composable
fun ProfileScreen(
    user: UserModel?,
    videos: List<VideoModel>,
    isSelf: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    isFollowing: Boolean = false,
    onToggleFollow: (() -> Unit)? = null,
    onToggleVerified: (() -> Unit)? = null,
    onLogout: (() -> Unit)? = null
) {
    if (user == null) return

    var selectedGridTab by remember { mutableIntStateOf(0) } // 0: Grid, 1: Liked, 2: Saved

    val myVideos = videos.filter { it.creatorId == user.id || it.creatorUsername == user.username }
    val likedVideos = videos.filter { it.isLiked }
    val savedVideos = videos.filter { it.isBookmarked }

    val displayedVideos = when (selectedGridTab) {
        0 -> if (myVideos.isEmpty()) videos else myVideos
        1 -> likedVideos
        else -> savedVideos
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TikTokBlack)
            .statusBarsPadding()
            .padding(top = 8.dp)
            .testTag("profile_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar dengan Tombol Back & Action Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("button_back_profile")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali ke Feed",
                        tint = Color.White
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }

            Text(
                text = user.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )

            if (isSelf && onLogout != null) {
                IconButton(
                    onClick = onLogout,
                    modifier = Modifier.testTag("button_logout")
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Keluar Akun",
                        tint = TikTokPink
                    )
                }
            } else {
                IconButton(
                    onClick = { },
                    modifier = Modifier.testTag("button_share_profile")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Bagikan Profil",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Avatar & Verified Badge
        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = "Avatar ${user.name}",
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .border(2.dp, TikTokPink, CircleShape)
                    .background(TikTokCardBg),
                contentScale = ContentScale.Crop
            )

            if (user.isVerified) {
                VerifiedBadge(
                    size = 22.dp,
                    modifier = Modifier.offset(x = 2.dp, y = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Username & Badge Status Text
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "@${user.username}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TikTokGray
            )
            if (user.isVerified) {
                Spacer(modifier = Modifier.width(6.dp))
                VerifiedBadge(size = 14.dp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Statistics Row (Following | Followers | Likes)
        val followersCount = if (!isSelf && isFollowing) user.followersCount + 1 else user.followersCount

        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileStatColumn(count = user.followingCount.toString(), label = "Mengikuti")
            Box(modifier = Modifier.height(20.dp).width(1.dp).background(TikTokCardBg))
            ProfileStatColumn(count = formatCount(followersCount), label = "Pengikut")
            Box(modifier = Modifier.height(20.dp).width(1.dp).background(TikTokCardBg))
            ProfileStatColumn(count = formatCount(user.totalLikesCount), label = "Suka")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bio Text
        Text(
            text = user.bio,
            fontSize = 13.sp,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons: Edit Profile / Toggle Verified (Self) ATAU Follow / Message (Creator Lain)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isSelf) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = TikTokDarkSurface),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Edit Profil", fontSize = 12.sp, color = Color.White)
                }

                // Tombol Khusus Atur Centang Biru untuk Penguji
                Button(
                    onClick = { onToggleVerified?.invoke() },
                    colors = ButtonDefaults.buttonColors(containerColor = if (user.isVerified) TikTokCyan.copy(alpha = 0.2f) else TikTokPink),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1.3f)
                        .testTag("button_toggle_verified")
                ) {
                    Icon(Icons.Default.Verified, contentDescription = null, tint = if (user.isVerified) TikTokCyan else Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (user.isVerified) "Verified Active ✓" else "Atur Centang Biru",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                // Tombol Follow / Mengikuti untuk Creator Lain
                Button(
                    onClick = { onToggleFollow?.invoke() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFollowing) TikTokDarkSurface else TikTokPink
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("button_follow_creator_profile")
                ) {
                    Icon(
                        imageVector = if (isFollowing) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isFollowing) "Mengikuti ✓" else "Ikuti",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = TikTokDarkSurface),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Pesan", fontSize = 13.sp, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Grid Tabs (My Videos | Liked Videos | Bookmarks)
        TabRow(
            selectedTabIndex = selectedGridTab,
            containerColor = TikTokBlack,
            contentColor = Color.White,
            divider = { HorizontalDivider(color = TikTokCardBg) }
        ) {
            Tab(
                selected = selectedGridTab == 0,
                onClick = { selectedGridTab = 0 },
                icon = { Icon(Icons.Default.GridOn, contentDescription = "Video Saya", tint = if (selectedGridTab == 0) Color.White else TikTokGray) }
            )
            Tab(
                selected = selectedGridTab == 1,
                onClick = { selectedGridTab = 1 },
                icon = { Icon(Icons.Default.Favorite, contentDescription = "Suka", tint = if (selectedGridTab == 1) TikTokPink else TikTokGray) }
            )
            Tab(
                selected = selectedGridTab == 2,
                onClick = { selectedGridTab = 2 },
                icon = { Icon(Icons.Default.Bookmark, contentDescription = "Disimpan", tint = if (selectedGridTab == 2) Color(0xFFFFC107) else TikTokGray) }
            )
        }

        // Grid Video Items
        if (displayedVideos.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (selectedGridTab) {
                        1 -> "Belum ada video yang disukai"
                        2 -> "Belum ada video disimpan"
                        else -> "Belum mengunggah video"
                    },
                    color = TikTokGray,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 80.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(displayedVideos) { video ->
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .background(TikTokCardBg)
                    ) {
                        Image(
                            painter = painterResource(id = video.coverImageRes),
                            contentDescription = video.caption,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = formatCount(video.likesCount), color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileStatColumn(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontSize = 17.sp, fontWeight = FontWeight.Black, color = Color.White)
        Text(text = label, fontSize = 11.sp, color = TikTokGray)
    }
}
