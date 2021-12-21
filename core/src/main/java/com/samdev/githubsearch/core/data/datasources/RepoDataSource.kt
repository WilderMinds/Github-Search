package com.samdev.githubsearch.core.data.datasources

import com.samdev.githubsearch.core.domain.RepoSearchResponse
import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 13/12/2021 at 2:44 PM
 */
interface RepoDataSource {

    suspend fun searchRepositories(query: String): Resource<RepoSearchResponse>

}