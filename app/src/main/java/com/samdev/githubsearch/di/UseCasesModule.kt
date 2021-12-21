package com.samdev.githubsearch.di

import com.samdev.githubsearch.core.data.repositories.LanguageRepository
import com.samdev.githubsearch.core.data.repositories.OwnerRepository
import com.samdev.githubsearch.core.data.repositories.RepoRepository
import com.samdev.githubsearch.core.usecases.*
import com.samdev.githubsearch.framework.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Sam
 * Created 14/12/2021 at 3:59 PM
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun providesGetAllRepositoryLanguagesUseCase(
        repository: LanguageRepository
    ): GetAllRepositoryLanguages = GetAllRepositoryLanguages(repository)


    @Singleton
    @Provides
    fun providesGetRepositoryContributorsUseCase(
        repository: OwnerRepository
    ): GetRepositoryContributors = GetRepositoryContributors(repository)


    @Singleton
    @Provides
    fun providesGetUserUseCase(
        repository: OwnerRepository
    ): GetUser = GetUser(repository)


    @Singleton
    @Provides
    fun providesSearchRepositoriesUseCase(
        repository: RepoRepository
    ): SearchRepositories = SearchRepositories(repository)


    @Singleton
    @Provides
    fun providesSortRepositoriesUseCase(): SortRepositories = SortRepositories()

    @Singleton
    @Provides
    fun providesUseCases(
        getAllRepositoryLanguages: GetAllRepositoryLanguages,
        getRepositoryContributors: GetRepositoryContributors,
        getUser: GetUser,
        searchRepositories: SearchRepositories,
        sortRepositories: SortRepositories
    ): UseCases = UseCases(
        getAllRepositoryLanguages,
        getRepositoryContributors,
        getUser,
        searchRepositories,
        sortRepositories
    )


}