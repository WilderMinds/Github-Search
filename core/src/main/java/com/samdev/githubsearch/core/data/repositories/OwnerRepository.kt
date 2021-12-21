package com.samdev.githubsearch.core.data.repositories

import com.samdev.githubsearch.core.data.datasources.OwnerDataSource
import com.samdev.githubsearch.core.domain.Owner
import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 13/12/2021 at 3:06 PM
 */
class OwnerRepository(private val ownerDataSource: OwnerDataSource) {

    suspend fun fetchUser(username: String): Resource<Owner> {
        return ownerDataSource.fetchByUsername(username)
    }

    suspend fun fetchContributorsForRepository(
        username: String,
        repository: String
    ): Resource<List<Owner>> {
        return ownerDataSource.fetchAllByUsernameAndRepository(username, repository)
    }


}