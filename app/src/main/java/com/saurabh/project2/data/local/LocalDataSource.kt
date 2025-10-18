package com.saurabh.project2.data.local

import com.saurabh.project2.data.model.User

class LocalDataSource(private val userPreferences: UserPreferences) {

    fun saveUser(user: User) {
        userPreferences.saveUser(user)
    }

    fun getUser(): User? {
        return userPreferences.getUser()
    }

    fun clearUser() {
        userPreferences.clearUser()
    }

    fun saveAuthToken(token: String) {
        userPreferences.saveAuthToken(token)
    }

    fun getAuthToken(): String? {
        return userPreferences.getAuthToken()
    }

    fun setTheme(theme: String) {
        userPreferences.setTheme(theme)
    }

    fun getTheme(): String {
        return userPreferences.getTheme()
    }

    fun setLanguage(language: String) {
        userPreferences.setLanguage(language)
    }

    fun getLanguage(): String {
        return userPreferences.getLanguage()
    }
}