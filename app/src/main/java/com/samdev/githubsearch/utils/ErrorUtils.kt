package com.samdev.githubsearch.utils

import android.content.Context
import com.samdev.githubsearch.R
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author Sam
 * Created 27/11/2021 at 1:55 PM
 */
object ErrorUtils {

    fun parseNetworkErrorResponse(e: Exception): Resource.Error {
        Timber.e(e)
        return when (e) {
            is HttpException -> {

                val errorBody: String? = e.response()?.errorBody()?.string()
                Timber.d(">>>>>>>>>$errorBody")
                if (!errorBody.isNullOrEmpty()) {
                    /*var json = JSONObject()

                    try {
                        json = JSONObject(errorBody)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }*/

                    Resource.Error(
                        errorMsg = errorBody,
                    )

                } else {
                    Resource.Error(
                        errorMsgId = R.string.generic_error_message,
                    )
                }
            }

            is SocketTimeoutException -> Resource.Error(
                errorMsgId = R.string.generic_error_message,
            )

            is IOException,
            is UnknownHostException -> Resource.Error(
                errorMsgId = R.string.no_internet_message
            )

            else -> Resource.Error(
                errorMsgId = R.string.generic_error_message
            )
        }
    }

    /**
     * Extract and return relevant error message.
     */
    fun getErrorMessage(context: Context, error: Resource.Error): String {
        val defaultMsg = context.getString(R.string.generic_error_message)
        return try {
            if (error.errorMsgId != -1) {
                context.getString(error.errorMsgId)
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error.errorMsg ?: defaultMsg
        }
    }
}