package com.samdev.githubsearch.data.models

import java.io.Serializable

/**
 * @author Sam
 * Created 28/11/2021 at 2:23 AM
 */
data class Language(
    var name: String = "",
    var loc: Long = 0
) : Serializable
