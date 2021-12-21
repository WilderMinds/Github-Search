package com.samdev.githubsearch.framework.datasources

import com.samdev.githubsearch.core.data.datasources.RepoDataSource
import com.samdev.githubsearch.core.domain.RepoSearchResponse
import com.samdev.githubsearch.core.utils.Resource
import com.samdev.githubsearch.framework.network.ApiService
import com.samdev.githubsearch.utils.makeApiRequest
import javax.inject.Inject

/**
 * @author Sam
 * Created 14/12/2021 at 3:26 PM
 */
class RemoteRepoDataSource @Inject constructor(
    private val apiService: ApiService
) : RepoDataSource {

    override suspend fun searchRepositories(query: String): Resource<RepoSearchResponse> {
        return makeApiRequest {
            apiService.searchRepositories(query)
        }
    }

}