package com.example.whatsappclone.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.navigation.Routes
import com.example.whatsappclone.util.AppPrefs
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current

    // LaunchedEffect(Unit) runs once, even when the composable recomposes
    LaunchedEffect(Unit) {
        delay(1000) // show the splash for 1 second
        val signedIn = AppPrefs.isSignedIn(context) && FirebaseAuth.getInstance().currentUser != null
        val destination: Routes = if (signedIn) Routes.HomeScreen else Routes.WelcomeScreen
        navController.navigate(destination) {
            // remove the splash from the back stack so Back never returns to it
            popUpTo(Routes.SplashScreen) { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Image(
            painter = painterResource(R.drawable.whatsapp_icon),
            contentDescription = null,
            modifier = Modifier.size(80.dp).align(Alignment.Center)
        )
        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("from", color = Color.Gray, fontSize = 14.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(R.drawable.meta), contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(4.dp))
                Text("Meta", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
