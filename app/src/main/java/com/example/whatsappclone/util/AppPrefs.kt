package com.example.whatsappclone.util

import android.content.Context

/** Tiny SharedPreferences wrapper: remembers whether the user already registered. */
object AppPrefs {
    private const val FILE_NAME = "app_prefs"
    private const val KEY_IS_SIGNED_IN = "is_signed_in"

    fun isSignedIn(context: Context): Boolean =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_IS_SIGNED_IN, false)

    fun setSignedIn(context: Context, value: Boolean) {
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_IS_SIGNED_IN, value)
            .apply()
    }
}


