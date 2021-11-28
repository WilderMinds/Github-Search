package com.samdev.githubsearch.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.samdev.githubsearch.R
import com.samdev.githubsearch.data.models.Language
import com.samdev.githubsearch.data.models.Owner
import com.samdev.githubsearch.data.repository.IRepository
import com.samdev.githubsearch.ui.details.adapters.LanguageAdapter
import com.samdev.githubsearch.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {


    private var _userResponse = MutableStateFlow<Resource<Owner>?>(null)
    val userResponse: StateFlow<Resource<Owner>?> = _userResponse

    private var _contributors = MutableStateFlow<Resource<List<Owner>>?>(null)
    val contributors: StateFlow<Resource<List<Owner>>?> = _contributors

    private var _languages = MutableStateFlow<Resource<JsonObject>?>(null)
    val languages: StateFlow<Resource<JsonObject>?> = _languages


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

    private fun fetchUser(userName: String) = viewModelScope.launch {
        _userResponse.value = Resource.Loading
        val response = repository.fetchUser(userName)
        _userResponse.value = response
    }


    private fun fetchContributors(userName: String, repo: String) = viewModelScope.launch {
        _contributors.value = Resource.Loading
        val response = repository.fetchContributors(userName, repo)
        _contributors.value = response
    }


    private fun fetchLanguages(userName: String, repo: String) = viewModelScope.launch {
        _languages.value = Resource.Loading
        val response = repository.fetchLanguages(userName, repo)
        _languages.value = response
    }


    /**
     * Convert the json object into a [Language] object which can
     * be passed into the corresponding list adapter [LanguageAdapter]
     */
    fun parseLanguagesObject(jsonObject: JsonObject): List<Language> {
        val result = mutableListOf<Language>()
        try {
            jsonObject.keySet().forEach {
                result.add(
                    Language(
                        name = it,
                        loc = jsonObject.get(it).asLong
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }


}