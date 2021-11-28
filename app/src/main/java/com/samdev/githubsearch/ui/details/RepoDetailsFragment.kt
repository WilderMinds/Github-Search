package com.samdev.githubsearch.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonObject
import com.samdev.githubsearch.R
import com.samdev.githubsearch.data.models.Owner
import com.samdev.githubsearch.databinding.FragmentRepoDetailsBinding
import com.samdev.githubsearch.extensions.loadUrl
import com.samdev.githubsearch.extensions.show
import com.samdev.githubsearch.ui.BaseFragment
import com.samdev.githubsearch.ui.details.adapters.ContributorsAdapter
import com.samdev.githubsearch.ui.details.adapters.LanguageAdapter
import com.samdev.githubsearch.utils.ErrorUtils
import com.samdev.githubsearch.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RepoDetailsFragment : BaseFragment() {

    companion object {
        fun newInstance() = RepoDetailsFragment()
    }

    private val viewModel: RepoDetailsViewModel by viewModels()
    private val args: RepoDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentRepoDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayExistingData()
        fetchAdditionalData()
        observeUserData()
        observeContributors()
        observeLanguages()
        initClickActions()
    }


    private fun initClickActions() {
        binding.apply {

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }


        }
    }


    /**
     * Fetch all the additional data we require
     */
    private fun fetchAdditionalData() {
        lifecycleScope.launchWhenResumed {
            val repo = args.repo
            if (repo == null) {
                mFragmentHelper?.showSnackBar(getString(R.string.generic_error_message))
                return@launchWhenResumed
            }

            val username = repo.owner?.login.orEmpty()
            val repoName = repo.name.orEmpty()
            viewModel.fetchAdditionalData(username, repoName)
        }
    }


    private fun observeUserData() {
        lifecycleScope.launchWhenResumed {
            viewModel.userResponse.collectLatest { res ->
                res?.let {
                    when (it) {
                        is Resource.Loading -> {

                        }
                        is Resource.Error -> {
                            handleError(it)
                        }
                        is Resource.Success -> {
                            displayUserData(it.data)
                        }
                    }
                }
            }
        }
    }


    private fun observeContributors() {
        lifecycleScope.launchWhenResumed {
            viewModel.contributors.collectLatest { res ->
                res?.let {
                    when (it) {
                        is Resource.Loading -> {

                        }
                        is Resource.Error -> {
                            handleError(it)
                        }
                        is Resource.Success -> {
                            displayContributors(it.data)
                        }
                    }
                }
            }
        }
    }


    private fun observeLanguages() {
        lifecycleScope.launchWhenResumed {
            viewModel.languages.collectLatest { res ->
                res?.let {
                    when (it) {
                        is Resource.Loading -> {

                        }
                        is Resource.Error -> {
                            handleError(it)
                        }
                        is Resource.Success -> {
                            displayLanguages(it.data)
                        }
                    }
                }
            }
        }
    }


    private fun handleError(error: Resource.Error) {
        context?.let {
            val msg = ErrorUtils.getErrorMessage(it, error)
            mFragmentHelper?.showSnackBar(msg)
        }
    }


    private fun displayExistingData() {
        args.repo?.let { repo ->
            binding.apply {
                tvRepoName.text = repo.fullName
                tvRepoDescription.text = repo.description

                val imageUrl = repo.owner?.avatarUrl
                ivAvatar.loadUrl(imageUrl)
            }
        }
    }


    private fun displayUserData(owner: Owner) {
        binding.apply {
            tvAuthorName.text = owner.name
            tvAuthorUsername.text = getString(R.string.s_username, owner.login.orEmpty())

            val companyName = owner.company.orEmpty()
            if (companyName.isNotBlank()) {
                tvWorkplace.show()
                tvWorkplace.text = getString(R.string.works_at_s, companyName)
            }

            val location = owner.location.orEmpty()
            if (location.isNotBlank()) {
                tvLocation.show()
                tvLocation.text = getString(R.string.lives_at_s, location)
            }
        }
    }


    private fun displayContributors(contributors: List<Owner>) {
        context?.let { c ->
            val contributorsAdapter = ContributorsAdapter()
            val gridLayoutManager = GridLayoutManager(c, 4)

            binding.rvContributors.apply {
                adapter = contributorsAdapter
                layoutManager = gridLayoutManager
            }

            contributorsAdapter.submitList(contributors)
        }
    }


    private fun displayLanguages(jsonObject: JsonObject) {
        val languageList = viewModel.parseLanguagesObject(jsonObject)
        context?.let { c ->
            val languageAdapter = LanguageAdapter()
            val gridLayoutManager = GridLayoutManager(c, 4)

            binding.rvLanguages.apply {
                adapter = languageAdapter
                layoutManager = gridLayoutManager
            }

            languageAdapter.submitList(languageList)
        }

    }
}