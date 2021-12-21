package com.samdev.githubsearch.core.usecases

import com.samdev.githubsearch.core.data.repositories.LanguageRepository
import com.samdev.githubsearch.core.utils.Resource

/**
 * @author Sam
 * Created 14/12/2021 at 2:28 PM
 */
class GetAllRepositoryLanguages(private val languageRepository: LanguageRepository) {

    suspend operator fun invoke(ownerName: String, repoName: String): Resource<Map<String, Long>> {
        return languageRepository.fetchLanguagesForRepository(ownerName, repoName)
    }

}