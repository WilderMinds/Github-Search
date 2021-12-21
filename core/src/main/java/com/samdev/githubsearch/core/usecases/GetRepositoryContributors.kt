package com.samdev.githubsearch.core.usecases

import com.samdev.githubsearch.core.data.repositories.OwnerRepository
import com.samdev.githubsearch.core.domain.Owner
import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 14/12/2021 at 2:23 PM
 */
class GetRepositoryContributors(private val ownerRepository: OwnerRepository) {

    suspend operator fun invoke(ownerName: String, repoName: String): Resource<List<Owner>> {
        return ownerRepository.fetchContributorsForRepository(ownerName, repoName)
    }

}