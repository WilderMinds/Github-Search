package com.samdev.githubsearch.core.domain

import java.io.Serializable

/**
 * @author Sam
 * Created 27/11/2021 at 1:43 PM
 */
data class RepoSearchResponse(
    val total_count: Long = 0,
    val incomplete_results: Boolean = false,
    val items: List<Repo> = listOf()
) : Serializable
