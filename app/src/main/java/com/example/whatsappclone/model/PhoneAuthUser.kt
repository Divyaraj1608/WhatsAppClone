package com.example.whatsappclone.model

/** Stored under  users/{uid}  in the Realtime Database. */
data class PhoneAuthUser(
    val userId: String = "",
    val phoneNumber: String = "",
    val name: String = "",
    val status: String = "",
    val profileImage: String? = null // Base64 string
)
