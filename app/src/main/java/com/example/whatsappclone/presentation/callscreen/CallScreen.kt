package com.example.whatsappclone.presentation.callscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
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
fun CallScreen(navController: NavHostController) {
    // Sample (dummy) data - replace with real data later
    val favorites = listOf(
        Favorite(R.drawable.salman_khan, "Salman Khan"),
        Favorite(R.drawable.mrbeast, "Mr Beast"),
        Favorite(R.drawable.akshay_kumar, "Akshay Kumar"),
        Favorite(R.drawable.bhuvan_bam, "Bhuvan Bam"),
        Favorite(R.drawable.sharukh_khan, "Shahrukh Khan")
    )
    val sampleCalls = listOf(
        Call(R.drawable.bhuvan_bam, "Bhuvan Bam", "Yesterday, 8:30", isMissed = true),
        Call(R.drawable.sharukh_khan, "Shahrukh Khan", "Today, 10 PM", isMissed = false),
        Call(R.drawable.akshay_kumar, "Akshay Kumar", "November, 8:42 AM", isMissed = true)
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            AppTopBar(
                title = "Calls",
                menuItems = listOf(
                    MenuAction("Settings") { navController.navigate(Routes.SettingsScreen) }
                )
            )
        },
        bottomBar = {   BottomNavigation(
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
                Icon(painterResource(R.drawable.add_call), contentDescription = "New call", modifier = Modifier.size(28.dp))
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
                    "Favorites",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                // horizontally scrollable row
                LazyRow {
                    items(favorites) { FavoriteItem(it) }
                }
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text("Start a new call", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Text(
                    "Recent calls",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(sampleCalls) { CallItemDesign(it) }
        }
    }
}
