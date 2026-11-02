package com.example.whatsappclone.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object SplashScreen : Routes
    @Serializable
    data object WelcomeScreen : Routes
    @Serializable
    data object UserRegistrationScreen : Routes
    @Serializable
    data object UserProfileSetScreen : Routes
    @Serializable
    data object HomeScreen : Routes
    @Serializable
    data object UpdateScreen : Routes
    @Serializable
    data object CommunitiesScreen : Routes
    @Serializable
    data object CallScreen : Routes
    @Serializable
    data object SettingsScreen : Routes



    @Serializable
    data object ChatScreen : Routes {
        const val route = "chat_screen/{phoneNumber}"
        fun createRoute(phoneNumber: String): String {
            return "chat_screen/$phoneNumber"
        }
    }


}

