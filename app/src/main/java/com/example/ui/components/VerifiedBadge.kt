package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.TikTokVerifiedBlue

/**
 * Komponen Verified Badge (Centang Biru) Kaimi-Clan
 * Menampilkan lingkaran biru kecil dengan ikon centang putih di tengahnya.
 */
@Composable
fun VerifiedBadge(
    modifier: Modifier = Modifier,
    size: Dp = 16.dp
) {
    Box(
        modifier = modifier
            .testTag("verified_badge")
            .size(size)
            .clip(CircleShape)
            .background(TikTokVerifiedBlue),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Verified Account Badge",
            tint = Color.White,
            modifier = Modifier.size(size * 0.7f)
        )
    }
}
