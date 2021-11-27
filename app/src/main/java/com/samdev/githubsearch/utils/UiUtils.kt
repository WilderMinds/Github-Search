package com.samdev.githubsearch.utils

import android.graphics.Color
import java.util.*

/**
 * @author Sam
 * Created 27/11/2021 at 9:55 PM
 */
object UiUtils {
    fun generateRandomColor(): Int {
        val rnd = Random()
        return Color.argb(
            255,
            rnd.nextInt(256),
            rnd.nextInt(256),
            rnd.nextInt(256)
        )
    }
}