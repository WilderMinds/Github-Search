package com.samdev.githubsearch.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.samdev.githubsearch.CoroutineRule
import com.samdev.githubsearch.data.models.RepoSearchResponse
import com.samdev.githubsearch.data.repository.DummyRepository
import com.samdev.githubsearch.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Sam
 * Created 27/11/2021 at 4:36 PM
 */
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: DummyRepository

    @Before
    fun setUp() {
        repository = DummyRepository()
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `repo search without internet connection returns error resource`() = runBlockingTest {
        repository.networkError = true
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
        repository.networkError = false
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