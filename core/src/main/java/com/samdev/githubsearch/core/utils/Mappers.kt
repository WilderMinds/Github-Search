package com.samdev.githubsearch.core.utils

import com.samdev.githubsearch.core.domain.Language

/**
 * @author Sam
 * Created 09/12/2021 at 1:28 PM
 */


/**
 * Convert map into a [Language] object
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