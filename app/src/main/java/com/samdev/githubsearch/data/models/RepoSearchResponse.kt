package com.samdev.githubsearch.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author Sam
 * Created 27/11/2021 at 1:43 PM
 */
data class RepoSearchResponse(

    @SerializedName("total_count")
    val totalCount: Long = 0,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean = false,

    val items: List<Repo> = listOf()
) : Serializable
