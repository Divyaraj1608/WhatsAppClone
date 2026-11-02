package com.example.whatsappclone.presentation.communityscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.bottomnavigation.BottomNavigation
import com.example.whatsappclone.presentation.common.AppTopBar
import com.example.whatsappclone.presentation.common.MenuAction
import com.example.whatsappclone.presentation.navigation.Routes
import com.example.whatsappclone.ui.theme.LightGreen

@Composable
fun CommunityScreen(navController: NavHostController) {
    val sampleCommunities = listOf(
        Communities(R.drawable.neat_roots, "Tech Enthusiasts", "256 members"),
        Communities(R.drawable.img, "Photography Lovers", "156 members"),
        Communities(R.drawable.meta, "Travellers United", "150 members")
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            AppTopBar(
                title = "Communities",
                menuItems = listOf(
                    MenuAction("Settings") { navController.navigate(Routes.SettingsScreen) }
                )
            )
        },
        bottomBar = {  BottomNavigation(
            navController = navController,
            selectedItem = 0,
            onClick = { index ->
                when (index) {
                    0 -> navController.navigate(Routes.HomeScreen)
                    1 -> navController.navigate(Routes.UpdateScreen)
                    2 -> navController.navigate(Routes.CommunitiesScreen)
                    3 -> navController.navigate(Routes.CallScreen)
                }
            }
        ) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            item {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 60.dp, vertical = 12.dp)
                ) {
                    Text("Start a new community", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Text(
                    "Your communities",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(sampleCommunities) { CommunityItemDesign(it) }
        }
    }
}
