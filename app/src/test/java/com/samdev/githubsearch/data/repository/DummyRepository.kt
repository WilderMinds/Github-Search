package com.samdev.githubsearch.data.repository

import com.google.gson.JsonObject
import com.samdev.githubsearch.data.models.Owner
import com.samdev.githubsearch.data.models.RepoSearchResponse
import com.samdev.githubsearch.utils.Resource

/**
 * @author Sam
 * Created 27/11/2021 at 1:46 PM
 */
class DummyRepository : IRepository {

    var networkError = false

    override suspend fun searchRepositories(query: String): Resource<RepoSearchResponse> {
        return if (networkError) {
            Resource.Error(errorMsg = "Unable to fetch data")
        } else {
            Resource.Success(RepoSearchResponse())
        }
    }

    override suspend fun fetchUser(username: String): Resource<Owner> {
        println("repo fetch user")
        return if (networkError) {
            Resource.Error(errorMsg = "Unable to fetch data")
        } else {
            Resource.Success(Owner())
        }
    }

    override suspend fun fetchContributors(
        username: String,
        repository: String
    ): Resource<List<Owner>> {
        println("repo fetch contribs")
        return if (networkError) {
            Resource.Error(errorMsg = "Unable to fetch data")
        } else {
            Resource.Success(listOf(Owner()))
        }
    }

    override suspend fun fetchLanguages(
        username: String,
        repository: String
    ): Resource<JsonObject> {
        println("repo fetch languages")
        return if (networkError) {
            Resource.Error(errorMsg = "Unable to fetch data")
        } else {
            Resource.Success(JsonObject().apply {
                addProperty("dart", 34231)
            })
        }
    }

}