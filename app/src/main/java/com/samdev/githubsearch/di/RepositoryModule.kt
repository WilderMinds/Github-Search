package com.samdev.githubsearch.di

import com.samdev.githubsearch.core.data.datasources.LanguageDataSource
import com.samdev.githubsearch.core.data.datasources.OwnerDataSource
import com.samdev.githubsearch.core.data.datasources.RepoDataSource
import com.samdev.githubsearch.core.data.repositories.LanguageRepository
import com.samdev.githubsearch.core.data.repositories.OwnerRepository
import com.samdev.githubsearch.core.data.repositories.RepoRepository
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
    fun providesLanguageRepository(
        dataSource: LanguageDataSource
    ): LanguageRepository = LanguageRepository(dataSource)


    @Singleton
    @Provides
    fun providesOwnerRepository(
        dataSource: OwnerDataSource
    ): OwnerRepository = OwnerRepository(dataSource)


    @Singleton
    @Provides
    fun providesRepoRepository(
        dataSource: RepoDataSource
    ): RepoRepository = RepoRepository(dataSource)

}