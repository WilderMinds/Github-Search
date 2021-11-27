package com.samdev.githubsearch.data.preference

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

/**
 * @author Sam
 * Created 27/11/2021 at 12:35 PM
 */
class PrefHelper(
    private val preferences: SharedPreferences
) {

    fun setPreference(name: String, value: String) {
        preferences.edit()
            .putString(name, value)
            .apply()
    }


    fun setPreference(name: String, value: Int) {
        preferences.edit()
            .putInt(name, value)
            .apply()
    }


    fun setPreference(name: String, value: Boolean) {
        preferences.edit()
            .putBoolean(name, value)
            .apply()
    }


    fun getPreference(name: String, default: String = ""): String {
        return preferences.getString(name, default) ?: default
    }


    fun getPreference(name: String, default: Int = -1): Int {
        return preferences.getInt(name, default)
    }


    fun getPreference(name: String, default: Boolean = false): Boolean {
        return preferences.getBoolean(name, default)
    }

    fun resetPrefs() {
        preferences.edit().clear().apply()
    }
}