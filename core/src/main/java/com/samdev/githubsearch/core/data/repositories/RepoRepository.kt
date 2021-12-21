package com.samdev.githubsearch.core.data.repositories

import com.samdev.githubsearch.core.data.datasources.RepoDataSource
import com.samdev.githubsearch.core.domain.RepoSearchResponse
import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 13/12/2021 at 3:08 PM
 */
class RepoRepository(private val repoDataSource: RepoDataSource) {

    suspend fun searchRepositories(query: String): Resource<RepoSearchResponse> {
        return repoDataSource.searchRepositories(query)
    }

}