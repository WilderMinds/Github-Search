package com.samdev.githubsearch.utils

/**
 * @author Sam
 * Created 27/11/2021 at 1:06 PM
 */
sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T): Resource<T>()

    /**
     * [errorMsg] will be used to pass error messages received from the API
     *
     * [errorMsgId] Will be used to pass error messages defined in outr string
     * resources.
     * We use the id directly instead of `ApplicationContext.getString(...)` because
     * of localisation
     *
     * @see ErrorUtils.getErrorMessage
     */
    data class Error(
        val errorMsg: String? = null,
        val errorMsgId: Int = -1,
        val data: Any? = null
    ): Resource<Nothing>()
}
