package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KaimiClanColorScheme = darkColorScheme(
    primary = TikTokPink,
    secondary = TikTokCyan,
    tertiary = TikTokVerifiedBlue,
    background = TikTokBlack,
    surface = TikTokDarkSurface,
    surfaceVariant = TikTokCardBg,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = TikTokLightGray
)

@Composable
fun KaimiClanTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = KaimiClanColorScheme,
        typography = Typography,
        content = content
    )
}

