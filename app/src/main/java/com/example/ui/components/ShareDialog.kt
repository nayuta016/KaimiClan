package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokDarkSurface
import com.example.ui.theme.TikTokPink

data class ShareOption(
    val title: String,
    val icon: ImageVector,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareDialog(
    onDismiss: () -> Unit,
    onOptionSelected: (String) -> Unit
) {
    val shareTargets = listOf(
        ShareOption("WhatsApp", Icons.Default.Send, Color(0xFF25D366)),
        ShareOption("Salin Tautan", Icons.Default.ContentCopy, TikTokCardBg),
        ShareOption("Pesan", Icons.Default.Email, Color(0xFF0084FF)),
        ShareOption("Simpan Video", Icons.Default.FileDownload, Color(0xFFFF9800)),
        ShareOption("Laporkan", Icons.Default.Report, Color(0xFFE53935))
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = TikTokDarkSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .testTag("share_dialog")
        ) {
            Text(
                text = "Bagikan ke",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(shareTargets) { option ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable {
                                onOptionSelected(option.title)
                                onDismiss()
                            }
                            .width(72.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(option.color),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = option.icon,
                                contentDescription = option.title,
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = option.title,
                            fontSize = 11.sp,
                            color = Color.White,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
