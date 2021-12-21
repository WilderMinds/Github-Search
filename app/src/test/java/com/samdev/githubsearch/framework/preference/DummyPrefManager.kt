package com.samdev.githubsearch.framework.preference

/**
 * @author Sam
 * Created 27/11/2021 at 12:43 PM
 */
class DummyPrefManager : IPrefManager {

    private var authenticated: Boolean = false
    private var authToken: String = "sfonasonfuo243oienf"

    override fun isLoggedIn(): Boolean {
        return authenticated
    }

    override fun setLoggedIn(loggedIn: Boolean) {
        authenticated = loggedIn
    }

    override fun getAuthToken(): String {
        return authToken
    }

    override fun setAuthToken(token: String) {
        authToken = token
    }

}