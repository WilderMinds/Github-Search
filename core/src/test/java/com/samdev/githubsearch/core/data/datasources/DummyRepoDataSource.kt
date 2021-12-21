package com.samdev.githubsearch.core.data.datasources

import com.samdev.githubsearch.core.domain.RepoSearchResponse
import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 15/12/2021 at 12:30 AM
 */
class DummyRepoDataSource : RepoDataSource {
    override suspend fun searchRepositories(query: String): Resource<RepoSearchResponse> {
        return Resource.Success(
            RepoSearchResponse(
                items = listOf()
            )
        )
    }
}