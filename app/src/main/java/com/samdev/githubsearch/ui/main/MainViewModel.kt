package com.samdev.githubsearch.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samdev.githubsearch.data.models.Repo
import com.samdev.githubsearch.data.models.RepoSearchResponse
import com.samdev.githubsearch.data.repository.IRepository
import com.samdev.githubsearch.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val cacheMap = mutableMapOf<String, RepoSearchResponse>()

    private var _searchResponse = MutableStateFlow<Resource<RepoSearchResponse>?>(null)
    val searchResponse: StateFlow<Resource<RepoSearchResponse>?> = _searchResponse


    fun searchRepositories(query: String) = viewModelScope.launch {
        _searchResponse.value = Resource.Loading

        // check and return from cache if exists
        val cachedResults = hasCache(query)
        if (cachedResults.isNotEmpty()) {
            val tempResults = Resource.Success(
                RepoSearchResponse(items = cachedResults)
            )
            _searchResponse.value = tempResults
        }

        // request from API
        val response = repository.searchRepositories(query)

        // cache result
        if (response is Resource.Success) {
            cacheResults(query, response.data)
        }

        // return api result finally
        _searchResponse.value = response
    }


    private fun cacheResults(query: String, response: RepoSearchResponse) {
        val mQuery = query.lowercase()
        cacheMap[mQuery] = response
    }


    private fun hasCache(query: String): List<Repo> {
        val mQuery = query.lowercase()
        if (cacheMap.containsKey(mQuery)) {
            return cacheMap[mQuery]?.items.orEmpty()
        }
        return emptyList()
    }

}