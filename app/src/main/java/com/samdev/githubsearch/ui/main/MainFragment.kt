package com.samdev.githubsearch.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samdev.githubsearch.App
import com.samdev.githubsearch.R
import com.samdev.githubsearch.core.domain.*
import com.samdev.githubsearch.core.usecases.SortRepositories
import com.samdev.githubsearch.core.utils.Resource
import com.samdev.githubsearch.databinding.FragmentMainBinding
import com.samdev.githubsearch.extensions.toggle
import com.samdev.githubsearch.extensions.toggleAnimateHideShow
import com.samdev.githubsearch.ui.BaseFragment
import com.samdev.githubsearch.utils.ErrorUtils
import com.samdev.githubsearch.utils.RepoClickCallback
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
    private var searchJob: Job? = null
    private var sortState = SortState.NONE

    // list
    private var mRepoList: MutableList<Repo> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        postponeEnterTransition()
        binding.root.doOnPreDraw { startPostponedEnterTransition() }
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
     * Apply corresponding comparator to list when a sort by chip is
     * checked
     */
    private fun initSortUI() {
        binding.btnSort.setOnClickListener {
            toggleSortUI()
        }

        // listen for sort by changed
        binding.chipGroupSort.setOnCheckedChangeListener { _, checkedId ->
            Timber.e("checked id -> $checkedId")
            sortState = when (checkedId) {
                R.id.chip_stars -> SortState.STARS
                R.id.chip_forks -> SortState.FORKS
                R.id.chip_updated -> SortState.UPDATED
                else -> SortState.NONE
            }
            sortRepositoryList()
        }
    }


    /**
     * Sort list based on the [SortState]
     * @see SortRepositories use case for implementation
     */
    private fun sortRepositoryList() {
        val sortedList = viewModel.sortList(sortState, mRepoList)
        repoAdapter.submitList(sortedList)
    }


    /**
     * Do nothing if there is not data available to sort by
     * Else show sorting view.
     *
     * This feature only works on the paid version
     */
    private fun toggleSortUI() {

        if (App.instance.freeVariant) {
            mFragmentHelper?.showSnackBar(getString(R.string.limited_feature))
            return
        }

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

            override fun onListItemClicked(repo: Repo, imageView: ImageView) {
                navigateToDetails(repo, imageView)
            }
        })
        repoAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.recyclerView.apply {
            adapter = repoAdapter
            layoutManager = linearLayoutManager
        }
    }


    /**
     * Show repository details
     *
     * This feature only works on the paid version
     */
    private fun navigateToDetails(repo: Repo, imageView: ImageView) {
        if (App.instance.freeVariant) {
            mFragmentHelper?.showSnackBar(getString(R.string.limited_feature))
            return
        }

        val extras = FragmentNavigatorExtras(
            imageView to "${repo.id}"
        )

        val directions = MainFragmentDirections.actionMainFragmentToRepoDetailsFragment(repo)
        findNavController().navigate(directions, extras)
    }


    /**
     * Launch external browser and view user details
     *
     * This feature only works on the paid version
     */
    private fun viewUserInfoInExternalBrowser(repo: Repo) {
        if (App.instance.freeVariant) {
            mFragmentHelper?.showSnackBar(getString(R.string.limited_feature))
            return
        }

        repo.owner?.html_url?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(browserIntent)
        }
    }


    /**
     * Trigger search on text changed.
     * If textfield is blank, show empty state
     */
    private fun listenSearchTextChanges() {
        binding.etSearch.addTextChangedListener { editable ->
            editable?.let {
                val query = it.toString()
                toggleEmptyState(query.isBlank())
                if (query.isBlank()) {
                    searchJob?.cancel()
                    repoAdapter.submitList(emptyList())
                    return@let
                }

                // search
                searchRepository(query)
            }
        }
    }


    private fun toggleEmptyState(show: Boolean) {
        binding.emptyState.root.toggle(show)
    }


    private fun toggleNoResultsState(show: Boolean) {
        binding.noResultsState.root.toggle(show)
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
                        is Resource.Loading -> toggleProgress(true)
                        is Resource.Error -> {
                            toggleProgress(false)
                            handleError(it)
                        }
                        is Resource.Success -> {
                            toggleProgress(false)

                            val list = it.data.items
                            mRepoList = list.toMutableList()

                            toggleNoResultsState(list.isEmpty())
                            sortRepositoryList()
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


    private fun toggleProgress(show: Boolean) {
        if (show) {
            toggleEmptyState(false)
            toggleNoResultsState(false)
        }
        binding.pbSearch.toggle(show)
    }

}