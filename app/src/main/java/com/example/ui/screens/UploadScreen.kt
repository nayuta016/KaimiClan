package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Videocam
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
import com.example.R
import com.example.ui.theme.TikTokBlack
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkSurface
import com.example.ui.theme.TikTokGray
import com.example.ui.theme.TikTokPink

/**
 * Halaman Unggah (Upload) Video Baru Kaimi-Clan
 */
@Composable
fun UploadScreen(
    onUploadSuccess: (caption: String, soundTitle: String, hashtags: String) -> Unit
) {
    var captionInput by remember { mutableStateOf("") }
    var soundInput by remember { mutableStateOf("Kaimi Clan Beat Original Sound") }
    var hashtagInput by remember { mutableStateOf("#KaimiClan #TikTokIndo #ShortVideo") }
    var isUploading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TikTokBlack)
            .statusBarsPadding()
            .padding(16.dp)
            .testTag("upload_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Unggah Video Kaimi-Clan",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Preview Video & Selection Box
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(TikTokDarkSurface)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_video_cover_1_1785430954622),
                contentDescription = "Preview Video",
                modifier = Modifier
                    .width(90.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = TikTokCardBg),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = TikTokCyan)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Pilih Galeri", color = Color.White, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = TikTokCardBg),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Videocam, contentDescription = null, tint = TikTokPink)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Rekam Langsung", color = Color.White, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Caption Input
        OutlinedTextField(
            value = captionInput,
            onValueChange = { captionInput = it },
            label = { Text("Deskripsi / Caption Video", color = TikTokGray) },
            placeholder = { Text("Tuliskan caption menarik video kamu...", color = TikTokGray) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_caption"),
            minLines = 3,
            maxLines = 4,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TikTokPink,
                unfocusedBorderColor = TikTokCardBg,
                focusedContainerColor = TikTokDarkSurface,
                unfocusedContainerColor = TikTokDarkSurface,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hashtags Input
        OutlinedTextField(
            value = hashtagInput,
            onValueChange = { hashtagInput = it },
            label = { Text("Hashtags (#)", color = TikTokGray) },
            leadingIcon = { Icon(Icons.Default.Tag, contentDescription = null, tint = TikTokCyan) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_hashtags"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TikTokCyan,
                unfocusedBorderColor = TikTokCardBg,
                focusedContainerColor = TikTokDarkSurface,
                unfocusedContainerColor = TikTokDarkSurface,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Music Sound Input
        OutlinedTextField(
            value = soundInput,
            onValueChange = { soundInput = it },
            label = { Text("Musik / Audio Sound", color = TikTokGray) },
            leadingIcon = { Icon(Icons.Default.MusicNote, contentDescription = null, tint = TikTokPink) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_sound"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TikTokPink,
                unfocusedBorderColor = TikTokCardBg,
                focusedContainerColor = TikTokDarkSurface,
                unfocusedContainerColor = TikTokDarkSurface,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.weight(1f))

        // Tombol Publish Video
        Button(
            onClick = {
                if (captionInput.isNotBlank()) {
                    isUploading = true
                    onUploadSuccess(captionInput, soundInput, hashtagInput)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("button_publish_video"),
            colors = ButtonDefaults.buttonColors(containerColor = TikTokPink),
            shape = RoundedCornerShape(25.dp),
            enabled = captionInput.isNotBlank() && !isUploading
        ) {
            Text(
                text = if (isUploading) "Mengunggah..." else "Publikasikan Video ✨",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
