package com.samdev.githubsearch.data.repository

import com.google.gson.JsonObject
import com.samdev.githubsearch.data.models.Owner
import com.samdev.githubsearch.data.models.RepoSearchResponse
import com.samdev.githubsearch.data.network.ApiService
import com.samdev.githubsearch.utils.Resource
import com.samdev.githubsearch.utils.makeApiRequest
import javax.inject.Inject

/**
 * @author Sam
 * Created 27/11/2021 at 1:05 PM
 *
 * Since app is relatively small, we will use a single repository for
 * the entire app
 */
class Repository @Inject constructor(
    private val apiService: ApiService
): IRepository {

    override suspend fun searchRepositories(query: String): Resource<RepoSearchResponse> {
        return makeApiRequest {
            apiService.searchRepositories(query)
        }
    }

    override suspend fun fetchUser(username: String): Resource<Owner> {
        return makeApiRequest {
            apiService.fetchUser(username)
        }
    }

    override suspend fun fetchContributors(
        username: String,
        repository: String
    ): Resource<List<Owner>> {
        return makeApiRequest {
            apiService.fetchContributors(username, repository)
        }
    }

    override suspend fun fetchLanguages(
        username: String,
        repository: String
    ): Resource<JsonObject> {
        return makeApiRequest {
            apiService.fetchLanguages(username, repository)
        }
    }


}