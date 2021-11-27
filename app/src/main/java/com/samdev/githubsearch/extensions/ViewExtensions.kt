package com.samdev.githubsearch.extensions

import android.view.View
import android.view.animation.AnimationUtils
import com.samdev.githubsearch.R

/**
 * @author Sam
 * Created 27/11/2021 at 11:30 PM
 */

fun View.animateShow() {
    visibility = View.VISIBLE
    val animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
    animation?.let {
        it.reset()
        clearAnimation()
        startAnimation(it)
    }
}


fun View.animateHide() {
    val animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
    animation?.let {
        it.reset()
        clearAnimation()
        startAnimation(it)
    }
    visibility = View.GONE
}


fun View.toggleAnimateHideShow() {
    if (isShown) {
        animateHide()
    } else {
        animateShow()
    }
}