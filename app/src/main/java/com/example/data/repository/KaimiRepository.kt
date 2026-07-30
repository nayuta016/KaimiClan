package com.example.data.repository

import com.example.R
import com.example.data.models.CommentModel
import com.example.data.models.NotificationModel
import com.example.data.models.NotificationType
import com.example.data.models.UserModel
import com.example.data.models.VideoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Repository Lokal Kaimi-Clan
 * Menyimpan data video, komentar, notifikasi, dan status login pengguna.
 */
class KaimiRepository {

    // Status Pengguna saat ini (Default logged out)
    private val _currentUser = MutableStateFlow<UserModel?>(
        UserModel(
            id = "user_001",
            username = "kaimi_creator",
            name = "Kaimi Clan Official",
            bio = "🔥 Welcoming all video creators in Kaimi-Clan! | TikTok Style App",
            avatarUrl = "https://picsum.photos/200/200?random=10",
            isVerified = true,
            followingCount = 142,
            followersCount = 12500,
            totalLikesCount = 48900
        )
    )
    val currentUser: StateFlow<UserModel?> = _currentUser.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(true) // Set default true for smooth demo preview
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    // Feed Video For You
    private val _forYouVideos = MutableStateFlow<List<VideoModel>>(emptyList())
    val forYouVideos: StateFlow<List<VideoModel>> = _forYouVideos.asStateFlow()

    // Feed Video Following
    private val _followingVideos = MutableStateFlow<List<VideoModel>>(emptyList())
    val followingVideos: StateFlow<List<VideoModel>> = _followingVideos.asStateFlow()

    // Map Komentar per Video ID
    private val _commentsMap = MutableStateFlow<Map<String, List<CommentModel>>>(emptyMap())
    val commentsMap: StateFlow<Map<String, List<CommentModel>>> = _commentsMap.asStateFlow()

    // Daftar Notifikasi
    private val _notifications = MutableStateFlow<List<NotificationModel>>(emptyList())
    val notifications: StateFlow<List<NotificationModel>> = _notifications.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        val initialForYou = listOf(
            VideoModel(
                id = "v1",
                creatorId = "c1",
                creatorName = "Ahmad Kaimi",
                creatorUsername = "ahmad_dance",
                creatorAvatarUrl = "https://picsum.photos/200/200?random=1",
                isCreatorVerified = true, // Centang Biru
                caption = "Tarian Kaimi-Clan terbaru! Cobain gerakan ini di rumah guys 🕺💥 #KaimiClan #TikTokIndo #Viral2026",
                hashtags = listOf("KaimiClan", "TikTokIndo", "Viral2026"),
                soundTitle = "Kaimi Clan Beat Original - Sound Remastered",
                soundArtist = "DJ Kaimi Remix",
                coverImageRes = R.drawable.img_video_cover_1_1785430954622,
                likesCount = 24500,
                commentsCount = 890,
                sharesCount = 1200,
                bookmarksCount = 3400,
                isLiked = false,
                isBookmarked = false,
                isFollowingCreator = false
            ),
            VideoModel(
                id = "v2",
                creatorId = "c2",
                creatorName = "Siti Barista",
                creatorUsername = "siti_coffee_art",
                creatorAvatarUrl = "https://picsum.photos/200/200?random=2",
                isCreatorVerified = true, // Centang Biru
                caption = "Bikin latte art swan dipagi hari ☕✨ Ada yang suka kopi espresso? #BaristaLife #CoffeeVibes #KaimiClan",
                hashtags = listOf("BaristaLife", "CoffeeVibes", "KaimiClan"),
                soundTitle = "Chill Morning Jazz Lounge - Kaimi Sound",
                soundArtist = "Coffee Beats",
                coverImageRes = R.drawable.img_video_cover_2_1785430972718,
                likesCount = 18200,
                commentsCount = 420,
                sharesCount = 610,
                bookmarksCount = 1950,
                isLiked = true,
                isBookmarked = true,
                isFollowingCreator = true
            ),
            VideoModel(
                id = "v3",
                creatorId = "c3",
                creatorName = "Budi CyberGamer",
                creatorUsername = "budi_gaming",
                creatorAvatarUrl = "https://picsum.photos/200/200?random=3",
                isCreatorVerified = false,
                caption = "Momen clutch epic saat tournament semifinal kemaren! 🔥🎮 Siapa yang mau mabar? #KaimiGaming #Esports #Clutch",
                hashtags = listOf("KaimiGaming", "Esports", "Clutch"),
                soundTitle = "Phonk Gaming Anthem 2026",
                soundArtist = "Cyber Audio",
                coverImageRes = R.drawable.img_video_cover_3_1785430990081,
                likesCount = 31900,
                commentsCount = 1120,
                sharesCount = 2300,
                bookmarksCount = 5600,
                isLiked = false,
                isBookmarked = false,
                isFollowingCreator = false
            )
        )

        _forYouVideos.value = initialForYou
        _followingVideos.value = listOf(initialForYou[1])

        // Initial Comments
        _commentsMap.value = mapOf(
            "v1" to listOf(
                CommentModel(
                    id = "com1",
                    videoId = "v1",
                    authorName = "Rina Official",
                    authorUsername = "rina_dance",
                    authorAvatarUrl = "https://picsum.photos/200/200?random=4",
                    isAuthorVerified = true,
                    commentText = "Kerenn banget gerakannya!! Besok mau coba bikin covernya 🔥",
                    timeAgo = "2j",
                    likesCount = 142,
                    isLiked = true
                ),
                CommentModel(
                    id = "com2",
                    videoId = "v1",
                    authorName = "Dewi Gaming",
                    authorUsername = "dewi_play",
                    authorAvatarUrl = "https://picsum.photos/200/200?random=5",
                    isAuthorVerified = false,
                    commentText = "Lagu backgroundnya judulnya apa mas?",
                    timeAgo = "4j",
                    likesCount = 28
                )
            ),
            "v2" to listOf(
                CommentModel(
                    id = "com3",
                    videoId = "v2",
                    authorName = "Kaimi Clan Official",
                    authorUsername = "kaimi_creator",
                    authorAvatarUrl = "https://picsum.photos/200/200?random=10",
                    isAuthorVerified = true,
                    commentText = "Latte art ter-smooth minggu ini! 👏☕",
                    timeAgo = "1j",
                    likesCount = 310,
                    isLiked = true
                )
            )
        )

        // Initial Notifications
        _notifications.value = listOf(
            NotificationModel(
                id = "n1",
                userAvatarUrl = "https://picsum.photos/200/200?random=1",
                username = "ahmad_dance",
                isVerified = true,
                type = NotificationType.LIKE,
                message = "menyukai video Anda.",
                timeAgo = "10m yang lalu",
                videoThumbnailRes = R.drawable.img_video_cover_1_1785430954622
            ),
            NotificationModel(
                id = "n2",
                userAvatarUrl = "https://picsum.photos/200/200?random=2",
                username = "siti_coffee_art",
                isVerified = true,
                type = NotificationType.COMMENT,
                message = "mengomentari: \"Wah makasih udah tonton ya!\"",
                timeAgo = "1j yang lalu",
                videoThumbnailRes = R.drawable.img_video_cover_2_1785430972718
            ),
            NotificationModel(
                id = "n3",
                userAvatarUrl = "https://picsum.photos/200/200?random=3",
                username = "budi_gaming",
                isVerified = false,
                type = NotificationType.FOLLOW,
                message = "mulai mengikuti Anda.",
                timeAgo = "3j yang lalu"
            )
        )
    }

    // Fungsi Toggle Login / Logout
    fun loginWithEmail(email: String, pass: String): Boolean {
        _isLoggedIn.value = true
        _currentUser.value = UserModel(
            id = "user_001",
            username = email.substringBefore("@").ifEmpty { "kaimi_user" },
            name = email.substringBefore("@").capitalize().ifEmpty { "Pengguna Kaimi" },
            bio = "Pengguna setia Kaimi-Clan short videos! 🚀",
            avatarUrl = "https://picsum.photos/200/200?random=12",
            isVerified = true
        )
        return true
    }

    fun loginWithGoogle(): Boolean {
        _isLoggedIn.value = true
        _currentUser.value = UserModel(
            id = "user_google",
            username = "kaimi_google_user",
            name = "Kaimi Google User",
            bio = "Logged in via Google Authentication ✨",
            avatarUrl = "https://picsum.photos/200/200?random=15",
            isVerified = true
        )
        return true
    }

    fun logout() {
        _isLoggedIn.value = false
    }

    // Toggle status Centang Biru (Verified Badge) manual untuk testing
    fun toggleVerifiedStatus() {
        _currentUser.update { user ->
            user?.copy(isVerified = !user.isVerified)
        }
    }

    // Ambil Profil Creator berdasarkan username
    fun getCreatorProfile(username: String): UserModel {
        val current = _currentUser.value
        if (current != null && (current.username == username || current.id == username)) {
            return current
        }

        return when (username) {
            "ahmad_dance", "c1" -> UserModel(
                id = "c1",
                username = "ahmad_dance",
                name = "Ahmad Kaimi",
                bio = "Official Dancer & Choreographer 🕺 | Kaimi-Clan Creator ✨ | Collab & Business DM 📩",
                avatarUrl = "https://picsum.photos/200/200?random=1",
                isVerified = true,
                followingCount = 210,
                followersCount = 48500,
                totalLikesCount = 184000
            )
            "siti_coffee_art", "c2" -> UserModel(
                id = "c2",
                username = "siti_coffee_art",
                name = "Siti Barista",
                bio = "Latte art enthusiast & Coffee Barista ☕✨ | Daily aesthetic vibes & coffee recipes",
                avatarUrl = "https://picsum.photos/200/200?random=2",
                isVerified = true,
                followingCount = 145,
                followersCount = 32400,
                totalLikesCount = 128000
            )
            "budi_gaming", "c3" -> UserModel(
                id = "c3",
                username = "budi_gaming",
                name = "Budi CyberGamer",
                bio = "Pro Esports Gamer & Streamer 🎮🔥 | Mobile Legends & PUBG Mobile highlights",
                avatarUrl = "https://picsum.photos/200/200?random=3",
                isVerified = false,
                followingCount = 92,
                followersCount = 19800,
                totalLikesCount = 86500
            )
            else -> UserModel(
                id = "c_gen_${username}",
                username = username,
                name = username.replace("_", " ").split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
                bio = "Kaimi-Clan Video Creator ✨ | Welcome to my official profile!",
                avatarUrl = "https://picsum.photos/200/200?random=10",
                isVerified = false,
                followingCount = 120,
                followersCount = 15400,
                totalLikesCount = 52000
            )
        }
    }

    // Toggle Like pada Video
    fun toggleLikeVideo(videoId: String) {
        _forYouVideos.update { list ->
            list.map { video ->
                if (video.id == videoId) {
                    val newIsLiked = !video.isLiked
                    val newCount = if (newIsLiked) video.likesCount + 1 else video.likesCount - 1
                    video.copy(isLiked = newIsLiked, likesCount = newCount)
                } else video
            }
        }
        _followingVideos.update { list ->
            list.map { video ->
                if (video.id == videoId) {
                    val newIsLiked = !video.isLiked
                    val newCount = if (newIsLiked) video.likesCount + 1 else video.likesCount - 1
                    video.copy(isLiked = newIsLiked, likesCount = newCount)
                } else video
            }
        }
    }

    // Toggle Bookmark / Save
    fun toggleBookmarkVideo(videoId: String) {
        _forYouVideos.update { list ->
            list.map { video ->
                if (video.id == videoId) {
                    val newBookmarked = !video.isBookmarked
                    val newCount = if (newBookmarked) video.bookmarksCount + 1 else video.bookmarksCount - 1
                    video.copy(isBookmarked = newBookmarked, bookmarksCount = newCount)
                } else video
            }
        }
    }

    // Toggle Follow Creator
    fun toggleFollowCreator(videoId: String) {
        _forYouVideos.update { list ->
            list.map { video ->
                if (video.id == videoId) {
                    video.copy(isFollowingCreator = !video.isFollowingCreator)
                } else video
            }
        }
    }

    // Tambah Komentar Baru
    fun addComment(videoId: String, commentText: String) {
        val user = _currentUser.value ?: return
        val newComment = CommentModel(
            id = "com_${System.currentTimeMillis()}",
            videoId = videoId,
            authorName = user.name,
            authorUsername = user.username,
            authorAvatarUrl = user.avatarUrl,
            isAuthorVerified = user.isVerified,
            commentText = commentText,
            timeAgo = "Baru saja",
            likesCount = 0
        )

        _commentsMap.update { currentMap ->
            val list = currentMap[videoId].orEmpty().toMutableList()
            list.add(0, newComment)
            currentMap + (videoId to list)
        }

        // Increment comments count on video
        _forYouVideos.update { list ->
            list.map { if (it.id == videoId) it.copy(commentsCount = it.commentsCount + 1) else it }
        }
    }

    // Tambah Video Baru (Upload)
    fun uploadVideo(caption: String, soundTitle: String, hashtagsText: String) {
        val user = _currentUser.value ?: return
        val hashtagList = hashtagsText.split(" ")
            .map { it.trim().removePrefix("#") }
            .filter { it.isNotEmpty() }

        val newVideo = VideoModel(
            id = "v_${System.currentTimeMillis()}",
            creatorId = user.id,
            creatorName = user.name,
            creatorUsername = user.username,
            creatorAvatarUrl = user.avatarUrl,
            isCreatorVerified = user.isVerified,
            caption = caption,
            hashtags = if (hashtagList.isEmpty()) listOf("KaimiClan", "Viral") else hashtagList,
            soundTitle = if (soundTitle.isBlank()) "Original Sound - ${user.username}" else soundTitle,
            soundArtist = user.name,
            coverImageRes = R.drawable.img_video_cover_1_1785430954622,
            likesCount = 0,
            commentsCount = 0,
            sharesCount = 0,
            bookmarksCount = 0,
            isLiked = false,
            isBookmarked = false,
            isFollowingCreator = true
        )

        _forYouVideos.update { listOf(newVideo) + it }
        _followingVideos.update { listOf(newVideo) + it }
    }
}
