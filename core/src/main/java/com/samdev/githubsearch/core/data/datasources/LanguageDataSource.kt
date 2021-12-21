package com.samdev.githubsearch.core.data.datasources

import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 13/12/2021 at 2:58 PM
 */
interface LanguageDataSource {

    suspend fun fetchLanguages(
        username: String,
        repository: String
    ): Resource<Map<String, Long>>

}