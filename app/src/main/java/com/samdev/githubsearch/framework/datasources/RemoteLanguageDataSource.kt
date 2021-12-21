package com.samdev.githubsearch.framework.datasources

import com.samdev.githubsearch.core.data.datasources.LanguageDataSource
import com.samdev.githubsearch.core.utils.Resource
import com.samdev.githubsearch.framework.network.ApiService
import com.samdev.githubsearch.utils.makeApiRequest
import javax.inject.Inject

/**
 * @author Sam
 * Created 14/12/2021 at 3:24 PM
 */

class RemoteLanguageDataSource @Inject constructor(
    private val apiService: ApiService
) : LanguageDataSource {

    override suspend fun fetchLanguages(
        username: String,
        repository: String
    ): Resource<Map<String, Long>> {
        return makeApiRequest {
            apiService.fetchLanguages(username, repository)
        }
    }

}