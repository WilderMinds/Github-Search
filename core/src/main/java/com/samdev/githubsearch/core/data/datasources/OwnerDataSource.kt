package com.samdev.githubsearch.core.data.datasources

import com.samdev.githubsearch.core.domain.Owner
import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 13/12/2021 at 2:55 PM
 */
interface OwnerDataSource {

    suspend fun fetchByUsername(username: String): Resource<Owner>

    suspend fun fetchAllByUsernameAndRepository(
        username: String,
        repository: String
    ): Resource<List<Owner>>
}