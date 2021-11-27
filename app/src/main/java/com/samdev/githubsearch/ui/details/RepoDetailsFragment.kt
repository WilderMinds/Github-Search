package com.samdev.githubsearch.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.samdev.githubsearch.databinding.FragmentRepoDetailsBinding
import com.samdev.githubsearch.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailsFragment : BaseFragment() {

    companion object {
        fun newInstance() = RepoDetailsFragment()
    }

    private val viewModel: RepoDetailsViewModel by viewModels()
    private lateinit var binding: FragmentRepoDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

}