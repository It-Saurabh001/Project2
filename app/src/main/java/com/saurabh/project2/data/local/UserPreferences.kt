package com.saurabh.project2.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.saurabh.project2.data.model.User
import androidx.core.content.edit

class UserPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        "buildmate_prefs",
        Context.MODE_PRIVATE
    )
    private val gson = Gson()

    fun saveUser(user: User) {
        preferences.edit { putString("user", gson.toJson(user)) }
    }

    fun getUser(): User? {
        val userJson = preferences.getString("user", null)
        return if (userJson != null) gson.fromJson(userJson, User::class.java) else null
    }

    fun clearUser() {
        preferences.edit { remove("user") }
    }

    fun saveAuthToken(token: String) {
        preferences.edit { putString("auth_token", token) }
    }

    fun getAuthToken(): String? = preferences.getString("auth_token", null)

    fun setTheme(theme: String) {
        preferences.edit { putString("theme", theme) }
    }

    fun getTheme(): String = preferences.getString("theme", "light") ?: "light"

    fun setLanguage(language: String) {
        preferences.edit { putString("language", language) }
    }

    fun getLanguage(): String = preferences.getString("language", "en") ?: "en"
}