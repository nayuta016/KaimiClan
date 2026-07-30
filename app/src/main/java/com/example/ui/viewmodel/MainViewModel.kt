package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.CommentModel
import com.example.data.models.NotificationModel
import com.example.data.models.UserModel
import com.example.data.models.VideoModel
import com.example.data.repository.KaimiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel Utama untuk Mengelola State UI Kaimi-Clan
 */
class MainViewModel(
    private val repository: KaimiRepository = KaimiRepository()
) : ViewModel() {

    val currentUser: StateFlow<UserModel?> = repository.currentUser
    val isLoggedIn: StateFlow<Boolean> = repository.isLoggedIn
    val forYouVideos: StateFlow<List<VideoModel>> = repository.forYouVideos
    val followingVideos: StateFlow<List<VideoModel>> = repository.followingVideos
    val commentsMap: StateFlow<Map<String, List<CommentModel>>> = repository.commentsMap
    val notifications: StateFlow<List<NotificationModel>> = repository.notifications

    fun login(email: String, pass: String): Boolean {
        return repository.loginWithEmail(email, pass)
    }

    fun loginGoogle(): Boolean {
        return repository.loginWithGoogle()
    }

    fun logout() {
        repository.logout()
    }

    fun toggleVerifiedStatus() {
        repository.toggleVerifiedStatus()
    }

    fun getCreatorProfile(username: String): UserModel {
        return repository.getCreatorProfile(username)
    }

    fun toggleLikeVideo(videoId: String) {
        repository.toggleLikeVideo(videoId)
    }

    fun toggleBookmarkVideo(videoId: String) {
        repository.toggleBookmarkVideo(videoId)
    }

    fun toggleFollowCreator(videoId: String) {
        repository.toggleFollowCreator(videoId)
    }

    fun addComment(videoId: String, commentText: String) {
        repository.addComment(videoId, commentText)
    }

    fun uploadVideo(caption: String, soundTitle: String, hashtagsText: String) {
        repository.uploadVideo(caption, soundTitle, hashtagsText)
    }
}
