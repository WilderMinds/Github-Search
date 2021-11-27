package com.samdev.githubsearch.ui.details

import androidx.lifecycle.ViewModel
import com.samdev.githubsearch.data.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
}