package com.samdev.githubsearch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @author Sam
 * Created 27/11/2021 at 2:24 PM
 */
@HiltAndroidApp
class App : Application() {

    lateinit var instance: App
        private set


    override fun onCreate() {
        super.onCreate()
        instance = this
        configureLogger()
    }

    private fun configureLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}