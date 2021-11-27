package com.samdev.githubsearch.data.network

import com.samdev.githubsearch.data.models.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Sam
 * Created 27/11/2021 at 1:01 PM
 */
interface ApiService {

    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): RepoSearchResponse

}