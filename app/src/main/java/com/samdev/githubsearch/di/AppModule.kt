package com.samdev.githubsearch.di

import android.content.Context
import com.samdev.githubsearch.data.preference.IPrefManager
import com.samdev.githubsearch.data.preference.PrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Sam
 * Created 27/11/2021 at 12:29 PM
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun providesPrefManager(
        @ApplicationContext context: Context
    ) : IPrefManager = PrefManager(context)


}