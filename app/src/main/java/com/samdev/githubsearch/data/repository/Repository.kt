package com.samdev.githubsearch.data.repository

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


}