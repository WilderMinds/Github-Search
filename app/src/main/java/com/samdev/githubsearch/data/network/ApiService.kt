package com.samdev.githubsearch.data.network

import com.google.gson.JsonObject
import com.samdev.githubsearch.data.models.Owner
import com.samdev.githubsearch.data.models.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Sam
 * Created 27/11/2021 at 1:01 PM
 */
interface ApiService {

    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): RepoSearchResponse

    @GET("users/{username}")
    suspend fun fetchUser(@Path("username") username: String): Owner

    @GET("repos/{username}/{repo}/contributors")
    suspend fun fetchContributors(
        @Path("username") username: String,
        @Path("repo") repository: String
    ): List<Owner>

    @GET("repos/{username}/{repo}/languages")
    suspend fun fetchLanguages(
        @Path("username") username: String,
        @Path("repo") repository: String
    ): JsonObject

}