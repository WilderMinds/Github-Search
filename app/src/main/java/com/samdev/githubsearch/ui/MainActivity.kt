package com.samdev.githubsearch.ui

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.samdev.githubsearch.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentHelper {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_GithubSearch)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun hideKeyboard() {
        toggleKeyBoardVisibility(
            show = false
        )
    }

    override fun showKeyboard() {
        toggleKeyBoardVisibility(
            show = true
        )
    }

    override fun showProgressIndicator() {
        TODO("Not yet implemented")
    }

    override fun hideProgressIndicator() {
        TODO("Not yet implemented")
    }

    override fun showSnackBar(msg: String) {
        this.currentFocus?.let {
            val snackBar = Snackbar.make(it, msg, Snackbar.LENGTH_LONG)
            snackBar.show()
        }
    }

    /**
     * Hide.Show keyboard with main activity as view anchor
     */
    private fun toggleKeyBoardVisibility(show: Boolean) {
        val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            this.currentFocus?.let { view ->
                if (show) {
                    // change flag to '0' if keyboard does not show
                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    view.clearFocus()
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}