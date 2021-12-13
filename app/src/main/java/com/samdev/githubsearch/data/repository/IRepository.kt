package com.samdev.githubsearch.data.repository

import com.samdev.githubsearch.data.models.Owner
import com.samdev.githubsearch.data.models.RepoSearchResponse
import com.samdev.githubsearch.utils.Resource

/**
 * @author Sam
 * Created 27/11/2021 at 1:05 PM
 */
interface IRepository {

    suspend fun searchRepositories(query: String): Resource<RepoSearchResponse>

    suspend fun fetchUser(username: String): Resource<Owner>

    suspend fun fetchContributors(username: String, repository: String): Resource<List<Owner>>

    suspend fun fetchLanguages(username: String, repository: String): Resource<Map<String, Long>>

}