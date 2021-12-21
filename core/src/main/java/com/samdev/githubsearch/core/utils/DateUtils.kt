package com.samdev.githubsearch.core.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Sam
 * Created 28/11/2021 at 10:23 AM
 */
object DateUtils {

    fun stringToDate(dateStr: String?): Date? {
        return try {
            if (dateStr.isNullOrBlank()) {
                return null
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("GMT")
            sdf.parse(dateStr)

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}