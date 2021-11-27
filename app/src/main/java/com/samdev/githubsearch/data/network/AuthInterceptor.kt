package com.samdev.githubsearch.data.network

import com.samdev.githubsearch.AppConstants
import com.samdev.githubsearch.data.preference.PrefManager
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Sam
 * Created 27/11/2021 at 12:50 PM
 */
class AuthInterceptor @Inject constructor(
    private val prefManager: PrefManager
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val authToken = prefManager.getAuthToken()

        Timber.e("request url = ${originalRequest.url}")
        Timber.e("token = $authToken")

        val newRequest = originalRequest
            .newBuilder()
            .addHeader("Authorization", "Bearer $authToken")
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}