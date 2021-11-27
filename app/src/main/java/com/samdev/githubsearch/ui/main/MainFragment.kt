package com.samdev.githubsearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.samdev.githubsearch.R
import com.samdev.githubsearch.data.models.Repo
import com.samdev.githubsearch.databinding.FragmentMainBinding
import com.samdev.githubsearch.extensions.toggleAnimateHideShow
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
        initSortUI()
    }


    /**
     * Do nothing if there is not data available to sort by
     * Else show sorting view
     */
    private fun initSortUI() {
        binding.btnSort.setOnClickListener {
            toggleSortUI()

        }

        // setup and listen for selected items
        binding.chipGroupSort.setOnCheckedChangeListener { _, checkedId ->
            Timber.e("checked id -> $checkedId")
            when (checkedId) {
                R.id.chip_stars -> {
                    Timber.e("filter by stars")
                }
                R.id.chip_forks -> {
                    Timber.e("filter by forks")
                }
                R.id.chip_updated -> {
                    Timber.e("filter by updated")
                }
                else -> {
                    // nothing selected
                    Timber.e("nothing selected")
                }
            }
        }
    }


    private fun toggleSortUI() {
        if (repoAdapter.itemCount == 0) {
            Toast.makeText(context, getString(R.string.no_items_to_sort), Toast.LENGTH_SHORT).show()
            return
        }

        binding.cvSortLayout.toggleAnimateHideShow()
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