package com.samdev.githubsearch.data.models

import com.google.gson.annotations.SerializedName

/**
 * @author Sam
 * Created 27/11/2021 at 11:47 PM
 */
data class License(
    @SerializedName("key")
    var key: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("spdx_id")
    var spdxId: String? = null,

    @SerializedName("url")
    var url: String? = null,

    @SerializedName("node_id")
    var nodeId: String? = null
)
