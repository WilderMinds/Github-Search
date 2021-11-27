package com.samdev.githubsearch.utils

import android.content.Context
import com.samdev.githubsearch.R

/**
 * @author Sam
 * Created 27/11/2021 at 1:55 PM
 */
object ErrorUtils {

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