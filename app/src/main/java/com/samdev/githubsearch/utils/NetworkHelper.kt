package com.samdev.githubsearch.utils

/**
 * @author Sam
 * Created 27/11/2021 at 1:49 PM
 */


suspend fun <T> makeApiRequest(
    call: suspend () -> T,
): Resource<T> {
    return try {
        val data = call.invoke()
        Resource.Success(data)
    } catch (throwable: Exception) {
        return ErrorUtils.parseNetworkErrorResponse(throwable)
    }
}