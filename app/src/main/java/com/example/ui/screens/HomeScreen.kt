package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.models.CommentModel
import com.example.data.models.UserModel
import com.example.data.models.VideoModel
import com.example.ui.components.CommentBottomSheet
import com.example.ui.components.ShareDialog
import com.example.ui.components.VerifiedBadge
import com.example.ui.theme.TikTokBlack
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokGray
import com.example.ui.theme.TikTokPink

/**
 * Feed Utama Video Vertikal (TikTok Style Home Screen)
 */
@Composable
fun HomeScreen(
    currentUser: UserModel?,
    forYouVideos: List<VideoModel>,
    followingVideos: List<VideoModel>,
    commentsMap: Map<String, List<CommentModel>>,
    onLikeVideo: (String) -> Unit,
    onBookmarkVideo: (String) -> Unit,
    onFollowCreator: (String) -> Unit,
    onAddComment: (String, String) -> Unit,
    getCreatorProfile: (String) -> UserModel,
    onToggleVerified: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(1) } // 0: Following, 1: For You
    val currentVideos = if (selectedTab == 0) followingVideos else forYouVideos

    var activeCommentVideoId by remember { mutableStateOf<String?>(null) }
    var isShareDialogOpen by remember { mutableStateOf(false) }
    var activeCreatorVideo by remember { mutableStateOf<VideoModel?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TikTokBlack)
            .testTag("home_screen")
    ) {
        if (currentVideos.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = { currentVideos.size })

            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val video = currentVideos[page]
                VideoCardItem(
                    video = video,
                    onLikeVideo = { onLikeVideo(video.id) },
                    onBookmarkVideo = { onBookmarkVideo(video.id) },
                    onFollowCreator = { onFollowCreator(video.id) },
                    onOpenComments = { activeCommentVideoId = video.id },
                    onOpenShare = { isShareDialogOpen = true },
                    onOpenCreatorProfile = { activeCreatorVideo = video }
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Belum ada video di feed ini", color = TikTokGray)
            }
        }

        // Top Navigation Tabs (Following | For You) - disembunyikan saat profile aktif
        if (activeCreatorVideo == null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tab Following
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { selectedTab = 0 }
                        .padding(horizontal = 12.dp)
                        .testTag("tab_following")
                ) {
                    Text(
                        text = "Mengikuti",
                        fontSize = 17.sp,
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 0) Color.White else TikTokGray
                    )
                    if (selectedTab == 0) {
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .width(28.dp)
                                .height(3.dp)
                                .background(TikTokPink, shape = RoundedCornerShape(2.dp))
                        )
                    }
                }

                Text(
                    text = " | ",
                    color = TikTokGray,
                    fontSize = 16.sp
                )

                // Tab For You
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { selectedTab = 1 }
                        .padding(horizontal = 12.dp)
                        .testTag("tab_for_you")
                ) {
                    Text(
                        text = "Untuk Anda",
                        fontSize = 17.sp,
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 1) Color.White else TikTokGray
                    )
                    if (selectedTab == 1) {
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .width(28.dp)
                                .height(3.dp)
                                .background(TikTokPink, shape = RoundedCornerShape(2.dp))
                        )
                    }
                }
            }
        }

        // Overlay Halaman Profil Creator (Di atas Feed)
        AnimatedVisibility(
            visible = activeCreatorVideo != null,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            activeCreatorVideo?.let { creatorVideo ->
                val isSelf = currentUser != null && (currentUser.id == creatorVideo.creatorId || currentUser.username == creatorVideo.creatorUsername)
                val creatorUser = if (isSelf) {
                    currentUser
                } else {
                    getCreatorProfile(creatorVideo.creatorUsername)
                }

                val creatorVideos = forYouVideos.filter {
                    it.creatorUsername == creatorVideo.creatorUsername || it.creatorId == creatorVideo.creatorId
                }.ifEmpty { listOf(creatorVideo) }

                val currentVideoState = forYouVideos.find { it.id == creatorVideo.id } ?: creatorVideo

                ProfileScreen(
                    user = creatorUser,
                    videos = creatorVideos,
                    isSelf = isSelf,
                    onBackClick = { activeCreatorVideo = null },
                    isFollowing = currentVideoState.isFollowingCreator,
                    onToggleFollow = { onFollowCreator(creatorVideo.id) },
                    onToggleVerified = onToggleVerified,
                    onLogout = onLogout
                )
            }
        }

        // Bottom Comments Sheet Dialog
        activeCommentVideoId?.let { videoId ->
            CommentBottomSheet(
                comments = commentsMap[videoId].orEmpty(),
                onDismiss = { activeCommentVideoId = null },
                onAddComment = { text ->
                    onAddComment(videoId, text)
                }
            )
        }

        // Share Dialog Sheet
        if (isShareDialogOpen) {
            ShareDialog(
                onDismiss = { isShareDialogOpen = false },
                onOptionSelected = { option -> }
            )
        }
    }
}

@Composable
fun VideoCardItem(
    video: VideoModel,
    onLikeVideo: () -> Unit,
    onBookmarkVideo: () -> Unit,
    onFollowCreator: () -> Unit,
    onOpenComments: () -> Unit,
    onOpenShare: () -> Unit,
    onOpenCreatorProfile: () -> Unit
) {
    // Animasi putaran piringan hitam musik
    val infiniteTransition = rememberInfiniteTransition(label = "vinyl_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("video_item_${video.id}")
    ) {
        // Thumbnail/Cover Video Vertikal Fullscreen
        Image(
            painter = painterResource(id = video.coverImageRes),
            contentDescription = "Cover Video ${video.caption}",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay Gradient Gelap agar Teks & Tombol Terbaca Jelas
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )

        // Indikator Autoplay / Play Icon Tengah
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Simulated subtle play overlay
        }

        // Overlay Konten Kiri Bawah (Creator, Caption, Sound)
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.78f)
                .padding(start = 16.dp, bottom = 24.dp)
        ) {
            // Username & Verified Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onOpenCreatorProfile() }
                    .testTag("creator_username_${video.id}")
            ) {
                Text(
                    text = "@${video.creatorUsername}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )

                if (video.isCreatorVerified) {
                    Spacer(modifier = Modifier.width(6.dp))
                    VerifiedBadge(size = 16.dp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Caption & Hashtags
            Text(
                text = video.caption,
                fontSize = 14.sp,
                color = Color.White,
                lineHeight = 18.sp,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Audio / Sound Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = "Audio Sound",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${video.soundTitle} - ${video.soundArtist}",
                    fontSize = 12.sp,
                    color = Color.White,
                    maxLines = 1
                )
            }
        }

        // Action Buttons Kanan Bawah (Avatar, Like, Comment, Bookmark, Share, Music Disc)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Creator Avatar dengan Tombol Follow (+)
            Box(contentAlignment = Alignment.BottomCenter) {
                AsyncImage(
                    model = video.creatorAvatarUrl,
                    contentDescription = "Avatar ${video.creatorName}",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, Color.White, CircleShape)
                        .background(TikTokCardBg)
                        .clickable { onOpenCreatorProfile() }
                        .testTag("creator_avatar_${video.id}"),
                    contentScale = ContentScale.Crop
                )

                if (!video.isFollowingCreator) {
                    Box(
                        modifier = Modifier
                            .offset(y = 8.dp)
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(TikTokPink)
                            .clickable { onFollowCreator() }
                            .testTag("button_follow_creator"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Follow",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Tombol Like
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onLikeVideo,
                    modifier = Modifier.testTag("button_like")
                ) {
                    Icon(
                        imageVector = if (video.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Suka Video",
                        tint = if (video.isLiked) TikTokPink else Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
                Text(
                    text = formatCount(video.likesCount),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Tombol Komentar
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onOpenComments,
                    modifier = Modifier.testTag("button_comments")
                ) {
                    Icon(
                        imageVector = Icons.Default.ModeComment,
                        contentDescription = "Komentar",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = formatCount(video.commentsCount),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Tombol Bookmark / Simpan
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onBookmarkVideo,
                    modifier = Modifier.testTag("button_bookmark")
                ) {
                    Icon(
                        imageVector = if (video.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Simpan Video",
                        tint = if (video.isBookmarked) Color(0xFFFFC107) else Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = formatCount(video.bookmarksCount),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Tombol Bagikan (Share)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onOpenShare,
                    modifier = Modifier.testTag("button_share")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Bagikan Video",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    text = formatCount(video.sharesCount),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Piringan Hitam Musik Berputar (Spinning Vinyl Disc)
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(42.dp)
                    .rotate(rotation)
                    .clip(CircleShape)
                    .background(TikTokCardBg)
                    .border(2.dp, Color.White.copy(alpha = 0.8f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = video.creatorAvatarUrl,
                    contentDescription = "Vinyl Sound Cover",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

// Format Angka Ribuan (e.g. 24500 -> 24.5K)
fun formatCount(count: Int): String {
    return when {
        count >= 1_000_000 -> String.format("%.1fM", count / 1_000_000.0)
        count >= 10_000 -> String.format("%.1fK", count / 1000.0)
        count >= 1000 -> String.format("%.1fK", count / 1000.0)
        else -> count.toString()
    }
}
