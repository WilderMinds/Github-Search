package com.samdev.githubsearch.core.usecases

import com.samdev.githubsearch.core.data.repositories.RepoRepository
import com.samdev.githubsearch.core.domain.RepoSearchResponse
import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 14/12/2021 at 2:04 PM
 */
class SearchRepositories(private val repoRepository: RepoRepository) {
    suspend operator fun invoke(query: String): Resource<RepoSearchResponse> {
        return repoRepository.searchRepositories(query)
    }
}