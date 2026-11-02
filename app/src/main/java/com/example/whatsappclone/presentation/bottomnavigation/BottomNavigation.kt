package com.example.whatsappclone.presentation.bottomnavigation


import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.whatsappclone.R

data class NavigationItem(
    val name: String,
    @DrawableRes val selectedItem: Int,
    @DrawableRes val unselected: Int
)

@Composable
fun BottomNavigation(
    navController: NavHostController,
    onClick: (Int) -> Unit,
    selectedItem: Int
) {
    val items = listOf(
        NavigationItem("Chats", R.drawable.chat_icon, R.drawable.outline_chat_24),
        NavigationItem("Updates", R.drawable.update_icon, R.drawable.update_icon),
        NavigationItem("Communities", R.drawable.baseline_groups_24, R.drawable.outline_groups_24),
        NavigationItem("Calls", R.drawable.telephone, R.drawable.outline_phone_24)
    )

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedItem == index
            NavigationBarItem(
                selected = isSelected,
                onClick = { onClick(index) },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (isSelected) item.selectedItem else item.unselected
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (isSelected) Color.DarkGray else Color.Black
                    )
                },
                label = {
                    Text(
                        text = item.name,
                        color = if (isSelected) Color.Black else Color.DarkGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorResource(id = R.color.mint_green)
                )
            )
        }
    }
}




/*

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.navigation.Routes
import com.example.whatsappclone.ui.theme.MintGreen

data class NavigationItem(
    val name: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val route: Routes
)

private val items = listOf(
    NavigationItem("Chats", R.drawable.chat_icon, R.drawable.outline_chat_24, Routes.HomeScreen),
    NavigationItem("Updates", R.drawable.update_icon, R.drawable.update_icon, Routes.UpdateScreen),
    NavigationItem("Communities", R.drawable.baseline_groups_24, R.drawable.outline_groups_24, Routes.CommunitiesScreen),
    NavigationItem("Calls", R.drawable.telephone, R.drawable.outline_phone_24, Routes.CallScreen)
)

/** @param selectedItem 0 = Chats, 1 = Updates, 2 = Communities, 3 = Calls */
@Composable
fun BottomNavigation(navController: NavHostController, selectedItem: Int) {
    NavigationBar(containerColor = Color.White) {
        items.forEachIndexed { index, item ->
            val selected = index == selectedItem
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(Routes.HomeScreen) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = {
                    Text(
                        text = item.name,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) Color.Black else Color.DarkGray
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(if (selected) item.selectedIcon else item.unselectedIcon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (selected) Color.Black else Color.DarkGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = MintGreen)
            )
        }
    }
}

*/
