package com.example.whatsappclone.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.whatsappclone.presentation.callscreen.CallScreen
import com.example.whatsappclone.presentation.chat.ChatScreen
import com.example.whatsappclone.presentation.chat.ChatScreen
import com.example.whatsappclone.presentation.communityscreen.CommunityScreen
import com.example.whatsappclone.presentation.home.HomeScreen
import com.example.whatsappclone.presentation.profile.UserProfileSetScreen
import com.example.whatsappclone.presentation.settings.SettingsScreen
import com.example.whatsappclone.presentation.splash.SplashScreen
import com.example.whatsappclone.presentation.updatescreen.UpdateScreen
import com.example.whatsappclone.presentation.userregistration.UserRegistrationScreen
import com.example.whatsappclone.presentation.viewmodels.BaseViewModel
import com.example.whatsappclone.presentation.welcome.WelcomeScreen

@Composable
fun WhatsAppNavigationSystem() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SplashScreen) {

        composable<Routes.SplashScreen> { SplashScreen(navController) }

        composable<Routes.WelcomeScreen> { WelcomeScreen(navController) }

        composable<Routes.UserRegistrationScreen> { UserRegistrationScreen(navController) }

        composable<Routes.UserProfileSetScreen> { UserProfileSetScreen(navController) }

        composable<Routes.HomeScreen> {
            val baseViewModel: BaseViewModel = hiltViewModel()
            HomeScreen(navController,baseViewModel)
        }

        composable<Routes.UpdateScreen> { UpdateScreen(navController) }

        composable<Routes.CommunitiesScreen> { CommunityScreen(navController) }

        composable<Routes.CallScreen> { CallScreen(navController) }

        composable<Routes.SettingsScreen> { SettingsScreen(navController) }

        composable(
            route = Routes.ChatScreen.route // "chat_screen/{phoneNumber}"
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments
                                         ?.getString("phoneNumber") ?: ""
            ChatScreen(
                otherPhoneNumber = phoneNumber,
                navController = navController
            )
        }

/*
       composable<Routes.ChatScreen> { entry ->
            val args = entry.toRoute<Routes.ChatScreen>()
            ChatScreen(navController, args.phoneNumber)
        }
        */

    }
}






