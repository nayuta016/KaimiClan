package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
 * Halaman Login dan Daftar (Authentication) Kaimi-Clan
 */
@Composable
fun AuthScreen(
    onLoginSuccess: (email: String, pass: String) -> Unit,
    onGoogleLoginSuccess: () -> Unit
) {
    var isRegisterMode by remember { mutableStateOf(false) }
    var emailInput by remember { mutableStateOf("kaimi@clan.id") }
    var passwordInput by remember { mutableStateOf("123456") }
    var nameInput by remember { mutableStateOf("Kaimi Member") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TikTokBlack)
            .padding(24.dp)
            .testTag("auth_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo Branding
            Image(
                painter = painterResource(id = R.drawable.img_app_logo_1785430934284),
                contentDescription = "Logo Kaimi-Clan",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Kaimi-Clan",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )

            Text(
                text = if (isRegisterMode) "Buat akun Kaimi-Clan baru" else "Masuk untuk melanjutkan nonton video",
                fontSize = 13.sp,
                color = TikTokGray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Form Fields
            if (isRegisterMode) {
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Nama Lengkap", color = TikTokGray) },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = TikTokGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_name"),
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
            }

            OutlinedTextField(
                value = emailInput,
                onValueChange = { emailInput = it },
                label = { Text("Email", color = TikTokGray) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = TikTokGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_email"),
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

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = passwordInput,
                onValueChange = { passwordInput = it },
                label = { Text("Kata Sandi (Password)", color = TikTokGray) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = TikTokGray) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_password"),
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

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Utama (Login / Daftar)
            Button(
                onClick = {
                    if (emailInput.isNotBlank() && passwordInput.isNotBlank()) {
                        onLoginSuccess(emailInput, passwordInput)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("button_submit_auth"),
                colors = ButtonDefaults.buttonColors(containerColor = TikTokPink),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = if (isRegisterMode) "Daftar Akun Baru" else "Masuk",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "atau", color = TikTokGray, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Google Sign-In
            OutlinedButton(
                onClick = onGoogleLoginSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("button_google_login"),
                shape = RoundedCornerShape(25.dp),
                border = ButtonDefaults.outlinedToolBarBorderDef()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "G", fontWeight = FontWeight.Black, color = Color(0xFF4285F4), fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Masuk dengan Google (Gmail)",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Toggle Mode Login <-> Register
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { isRegisterMode = !isRegisterMode }
            ) {
                Text(
                    text = if (isRegisterMode) "Sudah punya akun? " else "Belum punya akun? ",
                    color = TikTokGray,
                    fontSize = 14.sp
                )
                Text(
                    text = if (isRegisterMode) "Masuk" else "Daftar Sekarang",
                    color = TikTokCyan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun ButtonDefaults.outlinedToolBarBorderDef() =
    BorderStroke(1.dp, TikTokCardBg)
