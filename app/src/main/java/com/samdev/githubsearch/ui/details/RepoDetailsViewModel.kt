package com.samdev.githubsearch.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samdev.githubsearch.R
import com.samdev.githubsearch.core.domain.Owner
import com.samdev.githubsearch.core.utils.Resource
import com.samdev.githubsearch.framework.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {


    private var _userResponse = MutableStateFlow<Resource<Owner>?>(null)
    val userResponse: StateFlow<Resource<Owner>?> = _userResponse

    private var _contributors = MutableStateFlow<Resource<List<Owner>>?>(null)
    val contributors: StateFlow<Resource<List<Owner>>?> = _contributors

    private var _languages = MutableStateFlow<Resource<Map<String, Long>>?>(null)
    val languages: StateFlow<Resource<Map<String, Long>>?> = _languages


    /**
     * Validate inputs before making requests
     */
    fun fetchAdditionalData(userName: String, repo: String) = viewModelScope.launch {

        if (userName.isBlank() || repo.isBlank()) {
            _userResponse.value = Resource.Error(
                errorMsgId = R.string.generic_error_message
            )
            return@launch
        }

        fetchUser(userName)
        fetchContributors(userName, repo)
        fetchLanguages(userName, repo)
    }


    private suspend fun fetchUser(userName: String) {
        _userResponse.value = Resource.Loading
        val response = useCases.getUser(userName)
        _userResponse.value = response
        println("fetched user")
    }


    private suspend fun fetchContributors(userName: String, repo: String) {
        _contributors.value = Resource.Loading
        val response = useCases.getRepositoryContributors(userName, repo)
        _contributors.value = response
        println("fetched contributors")
    }


    private suspend fun fetchLanguages(userName: String, repo: String) {
        _languages.value = Resource.Loading
        val response = useCases.getAllRepositoryLanguages(userName, repo)
        _languages.value = response
        println("fetched languages")
    }
}