package com.samdev.githubsearch.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.samdev.githubsearch.CoroutineRule
import com.samdev.githubsearch.data.models.Owner
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
 * Created 29/11/2021 at 12:27 PM
 */
@ExperimentalCoroutinesApi
class RepoDetailsViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    private lateinit var viewModel: RepoDetailsViewModel
    private lateinit var repository: DummyRepository

    @Before
    fun setUp() {
        repository = DummyRepository()
        viewModel = RepoDetailsViewModel(repository)
    }


    @Test
    fun `additional data fetch without repo name returns error resource`() = runBlockingTest {
        val result = mutableListOf<Resource<Owner>?>()

        val job = launch {
            viewModel.fetchAdditionalData("query", "")
            viewModel.userResponse.toList(result)
        }

        val response = result.last()
        Truth.assertThat(response is Resource.Error).isTrue()
        job.cancel()
    }


    @Test
    fun `additional data fetch without author name returns error resource`() = runBlockingTest {
        val result = mutableListOf<Resource<Owner>?>()

        val job = launch {
            viewModel.fetchAdditionalData("", "github-search")
            viewModel.userResponse.toList(result)
        }

        val response = result.last()
        Truth.assertThat(response is Resource.Error).isTrue()
        job.cancel()
    }


    @Test
    fun `additional data fetch without internet connection returns error resource`() =
        runBlockingTest {
            repository.networkError = true

            val job = launch {
                viewModel.fetchAdditionalData("username", "github-search")
            }

            viewModel.userResponse.test {
                Truth.assertThat(expectMostRecentItem() is Resource.Error).isTrue()
            }

            viewModel.languages.test {
                Truth.assertThat(expectMostRecentItem() is Resource.Error).isTrue()
            }

            viewModel.contributors.test {
                Truth.assertThat(expectMostRecentItem() is Resource.Error).isTrue()
            }

            job.cancel()
        }


    @Test
    fun `additional data fetch with internet connection and valid data returns success resource`() =
        runBlockingTest {
            repository.networkError = false

            val job = launch {
                viewModel.fetchAdditionalData("username", "github-search")
            }

            viewModel.userResponse.test {
                Truth.assertThat(expectMostRecentItem() is Resource.Success).isTrue()
            }

            viewModel.languages.test {
                Truth.assertThat(expectMostRecentItem() is Resource.Success).isTrue()
            }

            viewModel.contributors.test {
                Truth.assertThat(expectMostRecentItem() is Resource.Success).isTrue()
            }

            job.cancel()
        }


    @Test
    fun `parse language json object with valid json structure returns list of languages`() {
        val inputJson = JsonObject().apply {
            addProperty("Haskell", 1233)
            addProperty("Kotlin", 12423)
            addProperty("Assembly", 123)
        }

        val result = viewModel.parseLanguagesObject(inputJson)
        Truth.assertThat(result).hasSize(3)
    }


    @Test
    fun `parse language json object with partially valid json structure returns valid portion`() {
        val subJsonObject = JsonObject().apply {
            addProperty("Assembly", 123)
        }
        val inputJson = JsonObject().apply {
            addProperty("Haskell", 1233)
            add("Kotlin", JsonArray())
            addProperty("Java", 10)
        }

        val result = viewModel.parseLanguagesObject(inputJson)
        Truth.assertThat(result).hasSize(2)
    }


}