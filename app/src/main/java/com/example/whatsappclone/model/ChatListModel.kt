package com.example.whatsappclone.model

/**
 * One row in the home-screen chat list.
 * All fields are nullable with defaults because Firebase needs an empty constructor.
 */

data class ChatListModel(
    var name: String? = null,
    var phoneNumber: String? = null,
    var image: String? = null,
    var userId: String? = null,
    var time: String? = null,
    var message: String? = null,
    var profileImage: String? = null
) {
    // Firebase Realtime Database के लिए नो-आर्ग्युमेंट कंस्ट्रक्टर
    constructor() : this(null, null,
        null, null, null,
        null, null)
}