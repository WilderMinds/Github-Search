package com.samdev.githubsearch.data.preference

import org.junit.Assert.*

/**
 * @author Sam
 * Created 27/11/2021 at 12:43 PM
 */
class DummyPrefManager(): IPrefManager {

    private var authenticated: Boolean = false

    override fun isLoggedIn(): Boolean {
        return authenticated
    }

    override fun setLoggedIn(loggedIn: Boolean) {
        authenticated = loggedIn
    }

}