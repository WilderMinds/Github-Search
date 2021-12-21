package com.samdev.githubsearch.framework.preference

/**
 * @author Sam
 * Created 27/11/2021 at 12:32 PM
 */
interface IPrefManager {

    fun isLoggedIn(): Boolean
    fun setLoggedIn(loggedIn: Boolean)

    fun getAuthToken(): String
    fun setAuthToken(token: String)

}