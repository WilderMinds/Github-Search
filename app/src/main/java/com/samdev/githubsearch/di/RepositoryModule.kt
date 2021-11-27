package com.samdev.githubsearch.di

import com.samdev.githubsearch.data.network.ApiService
import com.samdev.githubsearch.data.repository.IRepository
import com.samdev.githubsearch.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Sam
 * Created 27/11/2021 at 12:29 PM
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesRepository(
        apiService: ApiService
    ): IRepository = Repository(apiService)
}