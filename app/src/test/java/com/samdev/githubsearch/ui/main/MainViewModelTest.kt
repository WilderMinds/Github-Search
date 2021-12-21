package com.samdev.githubsearch.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.samdev.githubsearch.CoroutineRule
import com.samdev.githubsearch.core.data.datasources.RepoDataSource
import com.samdev.githubsearch.core.data.repositories.RepoRepository
import com.samdev.githubsearch.core.domain.RepoSearchResponse
import com.samdev.githubsearch.core.usecases.SearchRepositories
import com.samdev.githubsearch.core.utils.Resource
import com.samdev.githubsearch.framework.UseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author Sam
 * Created 27/11/2021 at 4:36 PM
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: MainViewModel
    // private lateinit var repository: DummyRepository


    @Mock
    private lateinit var useCases: UseCases

    @Mock
    private lateinit var repoDataSource: RepoDataSource

    @Mock
    private lateinit var repoRepository: RepoRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockUseCases()
        viewModel = MainViewModel(useCases)
    }


    private fun mockUseCases() = runBlockingTest {
        Mockito.verify(repoRepository).searchRepositories("")
        Mockito.verify(repoDataSource).searchRepositories("")
        Mockito.verify(useCases).searchRepositories("")

        //Mockito.`when`(repoDataSource).thenReturn(DummyRepoDataSource())
        Mockito.`when`(repoRepository).thenReturn(RepoRepository(repoDataSource))
        Mockito.`when`(useCases.searchRepositories).thenReturn(SearchRepositories(repoRepository))
    }

    @Test
    fun `repo search without internet connection returns error resource`() = runBlockingTest {
        // repository.networkError = true
        val result = mutableListOf<Resource<RepoSearchResponse>?>()

        val job = launch {
            viewModel.searchRepositories("query")
            viewModel.searchResponse.toList(result)
        }

        val response = result.last()
        Truth.assertThat(response is Resource.Error).isTrue()
        job.cancel()
    }


    @Test
    fun `repo search with internet connection returns success resource`() = runBlockingTest {
        // repository.networkError = false
        val result = mutableListOf<Resource<RepoSearchResponse>?>()

        val job = launch {
            viewModel.searchRepositories("query")
            viewModel.searchResponse.toList(result)
        }

        val response = result.last()
        Truth.assertThat(response is Resource.Success).isTrue()
        job.cancel()
    }

}