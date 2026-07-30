package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.models.CommentModel
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokDarkSurface
import com.example.ui.theme.TikTokGray
import com.example.ui.theme.TikTokPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheet(
    comments: List<CommentModel>,
    onDismiss: () -> Unit,
    onAddComment: (String) -> Unit
) {
    var newCommentText by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = TikTokDarkSurface,
        scrimColor = Color.Black.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .testTag("comment_sheet")
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "${comments.size} Komentar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup Komentar",
                        tint = Color.White
                    )
                }
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))

            // Daftar Komentar
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (comments.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Belum ada komentar. Jadilah yang pertama berkomentar!",
                                color = TikTokGray,
                                fontSize = 14.sp
                            )
                        }
                    }
                } else {
                    items(comments, key = { it.id }) { comment ->
                        CommentItem(comment = comment)
                    }
                }
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))

            // Field Input Komentar Baru
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .navigationBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newCommentText,
                    onValueChange = { newCommentText = it },
                    placeholder = { Text("Tambahkan komentar...", color = TikTokGray, fontSize = 14.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_comment"),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = TikTokCardBg,
                        focusedContainerColor = TikTokCardBg,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = TikTokPink,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (newCommentText.isNotBlank()) {
                            onAddComment(newCommentText)
                            newCommentText = ""
                        }
                    },
                    modifier = Modifier
                        .testTag("button_send_comment")
                        .clip(CircleShape)
                        .background(if (newCommentText.isNotBlank()) TikTokPink else TikTokCardBg)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Kirim Komentar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: CommentModel) {
    var isLiked by remember { mutableStateOf(comment.isLiked) }
    var likesCount by remember { mutableIntStateOf(comment.likesCount) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = comment.authorAvatarUrl,
            contentDescription = "Avatar ${comment.authorName}",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(TikTokCardBg),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = comment.authorName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color.White
                )
                if (comment.isAuthorVerified) {
                    Spacer(modifier = Modifier.width(4.dp))
                    VerifiedBadge(size = 13.dp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = comment.timeAgo,
                    fontSize = 11.sp,
                    color = TikTokGray
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = comment.commentText,
                fontSize = 13.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Balas",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TikTokGray,
                modifier = Modifier.clickable { }
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Suka Komentar",
                tint = if (isLiked) TikTokPink else TikTokGray,
                modifier = Modifier
                    .size(18.dp)
                    .clickable {
                        isLiked = !isLiked
                        likesCount = if (isLiked) likesCount + 1 else likesCount - 1
                    }
            )
            Text(
                text = likesCount.toString(),
                fontSize = 11.sp,
                color = TikTokGray
            )
        }
    }
}
