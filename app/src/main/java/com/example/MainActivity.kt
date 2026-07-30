package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BottomNavBar
import com.example.ui.components.NavTab
import com.example.ui.screens.*
import com.example.ui.theme.KaimiClanTheme
import com.example.ui.viewmodel.MainViewModel

/**
 * Entry Point Utama Aplikasi Kaimi-Clan
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KaimiClanTheme {
                KaimiClanApp()
            }
        }
    }
}

@Composable
fun KaimiClanApp(viewModel: MainViewModel = viewModel()) {
    var showSplash by remember { mutableStateOf(true) }
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val forYouVideos by viewModel.forYouVideos.collectAsStateWithLifecycle()
    val followingVideos by viewModel.followingVideos.collectAsStateWithLifecycle()
    val commentsMap by viewModel.commentsMap.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableStateOf(NavTab.HOME) }

    if (showSplash) {
        SplashScreen(
            onSplashFinished = { showSplash = false }
        )
    } else if (!isLoggedIn) {
        AuthScreen(
            onLoginSuccess = { email, pass ->
                viewModel.login(email, pass)
            },
            onGoogleLoginSuccess = {
                viewModel.loginGoogle()
            }
        )
    } else {
        Scaffold(
            bottomBar = {
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { tab -> selectedTab = tab }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding())
            ) {
                when (selectedTab) {
                    NavTab.HOME -> {
                        HomeScreen(
                            currentUser = currentUser,
                            forYouVideos = forYouVideos,
                            followingVideos = followingVideos,
                            commentsMap = commentsMap,
                            onLikeVideo = { videoId -> viewModel.toggleLikeVideo(videoId) },
                            onBookmarkVideo = { videoId -> viewModel.toggleBookmarkVideo(videoId) },
                            onFollowCreator = { videoId -> viewModel.toggleFollowCreator(videoId) },
                            onAddComment = { videoId, text -> viewModel.addComment(videoId, text) },
                            getCreatorProfile = { username -> viewModel.getCreatorProfile(username) },
                            onToggleVerified = { viewModel.toggleVerifiedStatus() },
                            onLogout = { viewModel.logout() }
                        )
                    }
                    NavTab.DISCOVER -> {
                        DiscoverScreen(videos = forYouVideos)
                    }
                    NavTab.UPLOAD -> {
                        UploadScreen(
                            onUploadSuccess = { caption, soundTitle, hashtags ->
                                viewModel.uploadVideo(caption, soundTitle, hashtags)
                                selectedTab = NavTab.HOME // Kembali ke feed setelah upload
                            }
                        )
                    }
                    NavTab.INBOX -> {
                        InboxScreen(notifications = notifications)
                    }
                    NavTab.PROFILE -> {
                        ProfileScreen(
                            user = currentUser,
                            videos = forYouVideos,
                            onToggleVerified = { viewModel.toggleVerifiedStatus() },
                            onLogout = { viewModel.logout() }
                        )
                    }
                }
            }
        }
    }
}
