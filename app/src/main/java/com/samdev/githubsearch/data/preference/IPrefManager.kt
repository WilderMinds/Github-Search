package com.samdev.githubsearch.data.preference

/**
 * @author Sam
 * Created 27/11/2021 at 12:32 PM
 */
interface IPrefManager {

    fun isLoggedIn(): Boolean
    fun setLoggedIn(loggedIn: Boolean)

}