package com.example.data.models

// Model Data Pengguna (User)
data class UserModel(
    val id: String,
    val username: String,
    val name: String,
    val bio: String,
    val avatarUrl: String,
    val isVerified: Boolean = false, // Status Centang Biru
    val followingCount: Int = 128,
    val followersCount: Int = 2540,
    val totalLikesCount: Int = 18900
)

// Model Data Video Pendek
data class VideoModel(
    val id: String,
    val creatorId: String,
    val creatorName: String,
    val creatorUsername: String,
    val creatorAvatarUrl: String,
    val isCreatorVerified: Boolean = false, // Badge centang biru di video
    val caption: String,
    val hashtags: List<String>,
    val soundTitle: String,
    val soundArtist: String,
    val coverImageRes: Int, // Resourcs gambar thumbnail video
    val likesCount: Int,
    val commentsCount: Int,
    val sharesCount: Int,
    val bookmarksCount: Int,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false,
    val isFollowingCreator: Boolean = false
)

// Model Data Komentar
data class CommentModel(
    val id: String,
    val videoId: String,
    val authorName: String,
    val authorUsername: String,
    val authorAvatarUrl: String,
    val isAuthorVerified: Boolean = false, // Centang biru di komentar
    val commentText: String,
    val timeAgo: String,
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val replies: List<CommentModel> = emptyList()
)

// Model Data Notifikasi
data class NotificationModel(
    val id: String,
    val userAvatarUrl: String,
    val username: String,
    val isVerified: Boolean = false,
    val type: NotificationType, // LIKE, COMMENT, FOLLOW
    val message: String,
    val timeAgo: String,
    val videoThumbnailRes: Int? = null
)

enum class NotificationType {
    LIKE, COMMENT, FOLLOW
}
