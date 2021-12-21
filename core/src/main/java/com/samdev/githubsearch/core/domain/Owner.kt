package com.samdev.githubsearch.core.domain

import java.io.Serializable

/**
 * @author Sam
 * Created 27/11/2021 at 1:36 PM
 */
data class Owner(
    var login: String? = null,
    var id: Int? = null,
    var node_id: String? = null,
    var avatar_url: String? = null,
    var gravatar_id: String? = null,
    var url: String? = null,
    var html_url: String? = null,
    var followers_url: String? = null,
    var following_url: String? = null,
    var gists_url: String? = null,
    var starred_url: String? = null,
    var subscriptions_url: String? = null,
    var organizations_url: String? = null,
    var repos_url: String? = null,
    var events_url: String? = null,
    var received_events_url: String? = null,
    var type: String? = null,
    var site_admin: Boolean = false,
    var name: String? = "",
    var company: String? = "",
    var blog: String? = "",
    var location: String? = "",
    var email: String? = "",
    var hireable: String? = null,
    var bio: String? = "",
    var twitter_username: String? = "",
    var public_repos: Int = 0,
    var public_gists: Int = 0,
    var followers: Int = 0,
) : Serializable
