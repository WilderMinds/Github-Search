package com.samdev.githubsearch.core.data.repositories

import com.samdev.githubsearch.core.data.datasources.LanguageDataSource
import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 13/12/2021 at 3:00 PM
 */
class LanguageRepository(private val languageDataSource: LanguageDataSource) {

    suspend fun fetchLanguagesForRepository(
        username: String,
        repository: String
    ): Resource<Map<String, Long>> {
        return languageDataSource.fetchLanguages(username, repository)
    }

}