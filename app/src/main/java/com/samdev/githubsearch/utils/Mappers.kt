package com.samdev.githubsearch.utils

import com.samdev.githubsearch.data.models.Language
import com.samdev.githubsearch.ui.details.adapters.LanguageAdapter

/**
 * @author Sam
 * Created 09/12/2021 at 1:28 PM
 */


/**
 * Convert map into a [Language] object which can
 * be passed into the corresponding list adapter [LanguageAdapter]
 */
fun Map<String, Long>.toLanguageList(): List<Language> {
    val result = mutableListOf<Language>()
    keys.forEach {
        val value = this[it]!!
        result.add(
            Language(
                name = it,
                loc = value
            )
        )
    }
    return result
}