package com.samdev.githubsearch.framework.datasources

import com.samdev.githubsearch.core.data.datasources.OwnerDataSource
import com.samdev.githubsearch.core.domain.Owner
import com.samdev.githubsearch.core.utils.Resource
import com.samdev.githubsearch.framework.network.ApiService
import com.samdev.githubsearch.utils.makeApiRequest
import javax.inject.Inject

/**
 * @author Sam
 * Created 14/12/2021 at 3:26 PM
 */
class RemoteOwnerDataSource @Inject constructor(
    private val apiService: ApiService
) : OwnerDataSource {

    override suspend fun fetchByUsername(username: String): Resource<Owner> {
        return makeApiRequest {
            apiService.fetchUser(username)
        }
    }

    override suspend fun fetchAllByUsernameAndRepository(
        username: String,
        repository: String
    ): Resource<List<Owner>> {
        return makeApiRequest {
            apiService.fetchContributors(username, repository)
        }
    }
}