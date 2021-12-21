package com.samdev.githubsearch.core.domain

import java.io.Serializable

/**
 * @author Sam
 * Created 27/11/2021 at 11:47 PM
 */
data class License(
    var key: String? = null,
    var name: String? = null,
    var spdx_id: String? = null,
    var url: String? = null,
    var node_id: String? = null
) : Serializable
