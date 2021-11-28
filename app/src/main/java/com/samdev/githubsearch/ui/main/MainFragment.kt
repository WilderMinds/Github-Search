package com.samdev.githubsearch.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.samdev.githubsearch.R
import com.samdev.githubsearch.data.models.*
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
import java.util.*


@AndroidEntryPoint
class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var repoAdapter: RepoAdapter

    // debounce search query
    private var mQuery: String = ""
    private var searchJob: Job? = null
    private var sortState = SortState.NONE

    // list
    private var mRepoList: MutableList<Repo> = mutableListOf()

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

        // listen for sort by changed
        binding.chipGroupSort.setOnCheckedChangeListener { _, checkedId ->
            Timber.e("checked id -> $checkedId")
            when (checkedId) {
                R.id.chip_stars -> {
                    sortState = SortState.STARS
                    applySortComparator(RepoStarsComparator())
                }
                R.id.chip_forks -> {
                    sortState = SortState.FORKS
                    applySortComparator(RepoForksComparator())
                }
                R.id.chip_updated -> {
                    sortState = SortState.UPDATED
                    applySortComparator(RepoUpdatedComparator())
                }
                else -> {
                    sortState = SortState.NONE
                    Timber.e("nothing selected")
                }
            }
        }
    }


    /**
     * Apply relevant sort comparator and reload the list
     */
    private fun applySortComparator(comparator: Comparator<Repo>) {
        val sortedList: List<Repo> = mRepoList.toList()
        Collections.sort(sortedList, comparator)
        repoAdapter.submitList(sortedList)
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
                viewUserInfoInExternalBrowser(repo)
            }

            override fun onListItemClicked(repo: Repo) {
                navigateToDetails(repo)
            }
        })

        binding.recyclerView.apply {
            adapter = repoAdapter
            layoutManager = linearLayoutManager
        }
    }


    private fun navigateToDetails(repo: Repo) {
        val directions = MainFragmentDirections.actionMainFragmentToRepoDetailsFragment(repo)
        findNavController().navigate(directions)
    }


    private fun viewUserInfoInExternalBrowser(repo: Repo) {
        repo.owner?.htmlUrl?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(browserIntent)
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
     * and delaying 800ms before triggering another search
     */
    private fun searchRepository(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launchWhenResumed {
            delay(800)
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
                            mRepoList.clear()
                            mRepoList.addAll(list)
                            applySortFilterIfNecessary()
                        }
                    }
                }
            }
        }
    }


    private fun applySortFilterIfNecessary() {
        when (sortState) {
            SortState.STARS -> applySortComparator(RepoStarsComparator())
            SortState.FORKS -> applySortComparator(RepoForksComparator())
            SortState.UPDATED -> applySortComparator(RepoUpdatedComparator())
            SortState.NONE -> repoAdapter.submitList(mRepoList)
        }
    }


    private fun handleError(error: Resource.Error) {
        context?.let {
            val errorMsg = ErrorUtils.getErrorMessage(it, error)
            mFragmentHelper?.showSnackBar(errorMsg)
        }
    }

}