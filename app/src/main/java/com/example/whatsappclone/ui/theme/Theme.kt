package com.example.whatsappclone.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightGreen = Color(0xFF25D366)
val DarkGreen = Color(0xFF075E54)
val MintGreen = Color(0xFFD9FDD3)

@Composable
fun WhatsAppCloneTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = DarkGreen,
            secondary = LightGreen,
            background = Color.White,
            surface = Color.White
        ),
        content = content
    )
}
