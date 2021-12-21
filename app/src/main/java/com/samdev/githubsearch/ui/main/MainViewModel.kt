package com.samdev.githubsearch.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samdev.githubsearch.core.domain.Repo
import com.samdev.githubsearch.core.domain.RepoSearchResponse
import com.samdev.githubsearch.core.domain.SortState
import com.samdev.githubsearch.core.utils.Resource
import com.samdev.githubsearch.framework.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
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
        val response = useCases.searchRepositories(query)

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


    fun sortList(sortState: SortState, sourceList: List<Repo>): List<Repo> {
        return useCases.sortRepositories(sortState, sourceList)
    }

}