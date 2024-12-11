package com.android.outfitly.data.local

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_LOGIN_TIMESTAMP = "login_timestamp"
        private const val SESSION_TIMEOUT_HOURS = 1
    }

    data class UserSession(
        val name: String,
        val email: String,
        val isLoggedIn: Boolean
    )

    fun getUserSession(): UserSession {
        return UserSession(
            name = getUserName(),
            email = getUserEmail(),
            isLoggedIn = isLoggedIn()
        )
    }

    fun saveLoginSession(name: String, email: String) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USER_NAME, name)
        editor.putString(KEY_USER_EMAIL, email)
        editor.putLong(KEY_LOGIN_TIMESTAMP, System.currentTimeMillis()) // Simpan waktu login
        editor.apply()
    }
    fun getUserName(): String {
        return prefs.getString(KEY_USER_NAME, "User") ?: "User"
    }

    fun getUserEmail(): String {
        return prefs.getString(KEY_USER_EMAIL, "") ?: ""
    }

    fun isLoggedIn(): Boolean {
        if (!prefs.getBoolean(KEY_IS_LOGGED_IN, false)) return false

        val loginTime = prefs.getLong(KEY_LOGIN_TIMESTAMP, 0)
        val currentTime = System.currentTimeMillis()
        val hoursSinceLogin = (currentTime - loginTime) / (1000 * 60 * 60)

        // Jika sesi sudah melebihi batas waktu, kembalikan false
        return hoursSinceLogin < SESSION_TIMEOUT_HOURS
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun logout() {
        val editor = prefs.edit()
        editor.remove(KEY_IS_LOGGED_IN)
        editor.remove(KEY_USER_NAME)
        editor.remove(KEY_USER_EMAIL)
        editor.remove(KEY_LOGIN_TIMESTAMP)
        editor.apply()
    }
}