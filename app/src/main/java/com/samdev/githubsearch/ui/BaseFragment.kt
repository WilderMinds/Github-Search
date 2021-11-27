package com.samdev.githubsearch.ui

import android.content.Context
import androidx.fragment.app.Fragment

/**
 * @author Sam
 * Created 27/11/2021 at 8:47 PM
 */
open class BaseFragment : Fragment() {

    var mFragmentHelper: FragmentHelper? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mFragmentHelper = if (context is FragmentHelper) {
            context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement FragmentHelper"
            )
        }
    }
}