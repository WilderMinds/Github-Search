package com.samdev.githubsearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.samdev.githubsearch.data.models.Repo
import com.samdev.githubsearch.databinding.FragmentMainBinding
import com.samdev.githubsearch.ui.BaseFragment
import com.samdev.githubsearch.utils.ErrorUtils
import com.samdev.githubsearch.utils.RepoClickCallback
import com.samdev.githubsearch.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var repoAdapter: RepoAdapter

    // debounce search query
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenSearchTextChanges()
        observeSearchResults()
        initRecyclerView()
    }


    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        repoAdapter = RepoAdapter(object : RepoClickCallback {
            override fun onUserImageClicked(repo: Repo) {
                Timber.e("onUserImageClicked")
            }

            override fun onListItemClicked(repo: Repo) {
                Timber.e("onListItemClicked")
            }
        })

        binding.recyclerView.apply {
            adapter = repoAdapter
            layoutManager = linearLayoutManager
        }
    }


    private fun listenSearchTextChanges() {
        binding.etSearch.addTextChangedListener { editable ->
            editable?.let {
                val query = it.toString()
                if (query.isBlank()) {
                    showEmptyState()
                    return@let
                }

                // search
                searchRepository(query)
            }
        }
    }


    private fun showEmptyState() {

    }


    /**
     * Debounce search trigger by cancelling active search job
     * and delaying 500ms before triggering another search
     */
    private fun searchRepository(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launchWhenResumed {
            delay(500)
            viewModel.searchRepositories(query)
        }
    }


    private fun observeSearchResults() {
        lifecycleScope.launchWhenResumed {
            viewModel.searchResponse.collectLatest { resource ->
                resource?.let {
                    mFragmentHelper?.hideKeyboard()
                    when (it) {
                        is Resource.Loading -> {

                        }

                        is Resource.Error -> {
                            handleError(it)
                        }

                        is Resource.Success -> {
                            val list = it.data.items
                            repoAdapter.submitList(list)
                        }
                    }
                }
            }
        }
    }

    private fun handleError(error: Resource.Error) {
        context?.let {
            val errorMsg = ErrorUtils.getErrorMessage(it, error)
            mFragmentHelper?.showSnackBar(errorMsg)
        }
    }

}