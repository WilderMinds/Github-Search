package com.samdev.githubsearch.data.repository

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

}