package com.samdev.githubsearch.core.usecases

import com.samdev.githubsearch.core.data.repositories.OwnerRepository
import com.samdev.githubsearch.core.domain.Owner
import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 14/12/2021 at 2:18 PM
 */
class GetUser(private val ownerRepository: OwnerRepository) {

    suspend operator fun invoke(userName: String): Resource<Owner> {
        return ownerRepository.fetchUser(userName)
    }

}