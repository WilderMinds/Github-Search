package com.samdev.githubsearch.utils

import com.google.common.truth.Truth
import org.junit.Test

/**
 * @author Sam
 * Created 28/11/2021 at 10:24 AM
 */
class DateUtilsTest {

    @Test
    fun convertStringToDate() {
        val string = "2021-11-27T12:17:04Z"
        val date = DateUtils.stringToDate(string)
        Truth.assertThat(date).isNotNull()
    }


    @Test
    fun `empty string date returns null`() {
        val string = ""
        val date = DateUtils.stringToDate(string)
        Truth.assertThat(date).isNull()
    }

    @Test
    fun `improperly date string returns null`() {
        val string = "sa fos qwvsd"
        val date = DateUtils.stringToDate(string)
        Truth.assertThat(date).isNull()
    }


    @Test
    fun `improperly formatted string date returns null`() {
        val string = "2021-11-27"
        val date = DateUtils.stringToDate(string)
        Truth.assertThat(date).isNull()
    }
}