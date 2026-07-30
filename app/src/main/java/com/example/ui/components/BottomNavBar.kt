package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.example.ui.theme.TikTokBlack
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokGray
import com.example.ui.theme.TikTokPink

enum class NavTab {
    HOME, DISCOVER, UPLOAD, INBOX, PROFILE
}

/**
 * Bottom Navigation Bar TikTok Style Kaimi-Clan
 */
@Composable
fun BottomNavBar(
    selectedTab: NavTab,
    onTabSelected: (NavTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TikTokBlack)
            .navigationBarsPadding()
            .height(60.dp)
            .padding(horizontal = 8.dp)
            .testTag("bottom_nav_bar"),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavItem(
            label = "Beranda",
            icon = Icons.Default.Home,
            isSelected = selectedTab == NavTab.HOME,
            onClick = { onTabSelected(NavTab.HOME) },
            testTag = "nav_home"
        )

        NavItem(
            label = "Temukan",
            icon = Icons.Default.Search,
            isSelected = selectedTab == NavTab.DISCOVER,
            onClick = { onTabSelected(NavTab.DISCOVER) },
            testTag = "nav_discover"
        )

        // Center Upload (+) Button dengan Aksen TikTok Pink & Cyan
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(30.dp)
                .clickable { onTabSelected(NavTab.UPLOAD) }
                .testTag("nav_upload"),
            contentAlignment = Alignment.Center
        ) {
            // Shadow Cyan Layer
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = (-3).dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(TikTokCyan)
            )
            // Shadow Pink Layer
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = 3.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(TikTokPink)
            )
            // Center White Box
            Box(
                modifier = Modifier
                    .width(38.dp)
                    .height(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Unggah Video",
                    tint = TikTokBlack,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        NavItem(
            label = "Kotak Masuk",
            icon = Icons.Default.Inbox,
            isSelected = selectedTab == NavTab.INBOX,
            onClick = { onTabSelected(NavTab.INBOX) },
            testTag = "nav_inbox"
        )

        NavItem(
            label = "Profil",
            icon = Icons.Default.Person,
            isSelected = selectedTab == NavTab.PROFILE,
            onClick = { onTabSelected(NavTab.PROFILE) },
            testTag = "nav_profile"
        )
    }
}

@Composable
fun NavItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp)
            .testTag(testTag)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color.White else TikTokGray,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) Color.White else TikTokGray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
