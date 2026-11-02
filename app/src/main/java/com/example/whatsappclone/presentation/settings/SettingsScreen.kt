package com.example.whatsappclone.presentation.settings

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.navigation.Routes
import com.example.whatsappclone.presentation.viewmodels.PhoneAuthViewModel
import com.example.whatsappclone.ui.theme.DarkGreen

private data class SettingsRow(@DrawableRes val icon: Int, val title: String)

private val rows = listOf(
    SettingsRow(R.drawable.outline_account_circle_24, "Account"),
    SettingsRow(R.drawable.outline_lock_24, "Privacy"),
    SettingsRow(R.drawable.outline_chat_24, "Chats"),
    SettingsRow(R.drawable.baseline_notifications_none_24, "Notifications"),
    SettingsRow(R.drawable.baseline_data_usage_24, "Storage and data"),
    SettingsRow(R.drawable.baseline_help_outline_24, "Help")
)

/** Settings list  with a working Log out button. */
@Composable
fun SettingsScreen(
    navController: NavHostController,
    phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity

    Column(modifier = Modifier.fillMaxSize().background(Color.White).statusBarsPadding()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "Back")
            }
            Text("Settings", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(row.icon), contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.DarkGray)
                Spacer(Modifier.width(20.dp))
                Text(row.title, fontSize = 16.sp)
            }
        }
        Button(
            onClick = {
                phoneAuthViewModel.signOut(activity)
                navController.navigate(Routes.UserRegistrationScreen) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        ) {
            Text("Log out", color = Color.White)
        }
    }
}
