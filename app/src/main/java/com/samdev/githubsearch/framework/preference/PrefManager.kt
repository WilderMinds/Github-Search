package com.samdev.githubsearch.framework.preference

import android.content.Context
import android.content.SharedPreferences
import com.samdev.githubsearch.AppConstants
import javax.inject.Inject

/**
 * @author Sam
 * Created 27/11/2021 at 12:33 PM
 */
class PrefManager @Inject constructor (
    context: Context,
): IPrefManager {

    private var helper: PrefHelper

    init {
        val preferences: SharedPreferences = context.getSharedPreferences(
            AppConstants.PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        helper = PrefHelper(preferences)
    }


    override fun isLoggedIn(): Boolean {
        return helper.getPreference(AppConstants.PREF_IS_LOGGED_IN, false)
    }

    override fun setLoggedIn(loggedIn: Boolean) {
        helper.setPreference(AppConstants.PREF_IS_LOGGED_IN, loggedIn)
    }

    override fun getAuthToken(): String {
        return helper.getPreference(AppConstants.PREF_AUTH_TOKEN, "")
    }

    override fun setAuthToken(token: String) {
        helper.setPreference(AppConstants.PREF_AUTH_TOKEN, token)
    }


}