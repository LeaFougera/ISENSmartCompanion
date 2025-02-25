package fr.isen.fougera.isensmartcompanion

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val PREFERENCE_NAME = "user_preferences"
    private const val KEY_NOTIFICATION_SUBSCRIBED = "notification_subscribed"

    fun isNotificationSubscribed(context: Context): Boolean {
        val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return preferences.getBoolean(KEY_NOTIFICATION_SUBSCRIBED, false)
    }

    fun setNotificationSubscribed(context: Context, isSubscribed: Boolean) {
        val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean(KEY_NOTIFICATION_SUBSCRIBED, isSubscribed)
        editor.apply()
    }
}