package com.samdev.githubsearch.data.repository

import com.samdev.githubsearch.data.models.RepoSearchResponse
import com.samdev.githubsearch.utils.Resource

/**
 * @author Sam
 * Created 27/11/2021 at 1:05 PM
 */
interface IRepository {

    suspend fun searchRepositories(query: String): Resource<RepoSearchResponse>

}