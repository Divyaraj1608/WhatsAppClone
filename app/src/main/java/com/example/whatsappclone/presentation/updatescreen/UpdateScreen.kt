package com.example.whatsappclone.presentation.updatescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
fun UpdateScreen(navController: NavHostController) {
    // Sample (dummy) data - replace with real data later
    val sampleStatus = listOf(
        StatusData(R.drawable.disha_patani, "Disha Patani", "10 minutes ago"),
        StatusData(R.drawable.tripti_dimri, "Tripti Dimri", "2 minutes ago"),
        StatusData(R.drawable.carryminati, "Carry Minati", "5 minutes ago")
    )
    val sampleChannels = listOf(
        Channels(R.drawable.neat_roots, "Neat Roots", "Latest news in tech"),
        Channels(R.drawable.img, "Food Lover", "Discover new recipes"),
        Channels(R.drawable.meta, "Meta", "Explore the world")
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            AppTopBar(
                title = "Updates",
                showCamera = true,
                menuItems = listOf(
                    MenuAction("Status privacy"),
                    MenuAction("Create channel"),
                    MenuAction("Settings") { navController.navigate(Routes.SettingsScreen) }
                )
            )
        },
        bottomBar = { BottomNavigation(
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
        )
                    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = LightGreen,
                contentColor = Color.White,
                modifier = Modifier.size(65.dp)
            ) {
                Icon(painterResource(R.drawable.camera), contentDescription = "Camera", modifier = Modifier.size(28.dp))
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            item {
                Text(
                    "Status",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
                MyStatus()
            }
            items(sampleStatus) { StatusItem(it) }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)
                Text(
                    "Channels",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
                Text(
                    "Stay updated on topics that matter to you. Find channels to follow below.",
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "Find channels to follow",
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
            }
            items(sampleChannels) { ChannelItemDesign(it) }
        }
    }
}
