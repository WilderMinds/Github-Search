package com.samdev.githubsearch.ui

/**
 * @author Sam
 * Created 27/11/2021 at 8:46 PM
 */
interface FragmentHelper {
    fun hideKeyboard()
    fun showKeyboard()
    fun showProgressIndicator()
    fun hideProgressIndicator()
    fun showSnackBar(msg: String)
}