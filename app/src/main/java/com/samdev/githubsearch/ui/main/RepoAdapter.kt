package com.samdev.githubsearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samdev.githubsearch.R
import com.samdev.githubsearch.core.domain.Repo
import com.samdev.githubsearch.databinding.ItemRepositoryBinding
import com.samdev.githubsearch.extensions.loadUrl
import com.samdev.githubsearch.utils.RepoClickCallback
import com.samdev.githubsearch.utils.UiUtils

class RepoAdapter(
    private val callback: RepoClickCallback
) : ListAdapter<Repo, RepoAdapter.ItemViewHolder>(RepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepositoryBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ItemViewHolder(
        private val binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repo) = with(itemView) {

            // Set data to your item view here
            val username = item.owner?.login.orEmpty()
            binding.tvAuthorName.text = context.getString(R.string.s_username, username)
            binding.tvRepoName.text = item.full_name
            binding.tvDescription.text = item.description
            binding.tvWatchers.text = "${item.watchers_count ?: 0}"
            binding.tvForks.text = "${item.forks_count ?: 0}"
            binding.tvIssues.text = "${item.open_issues_count ?: 0}"

            // using the id because we want a unique transition name
            binding.ivAvatar.transitionName = "${item.id}"
            item.owner?.avatar_url?.let { imageUrl ->
                binding.ivAvatar.loadUrl(imageUrl)
            }


            // assign random color
            val randomColor = UiUtils.generateRandomColor()
            binding.clUserDetails.setBackgroundColor(randomColor)

            // on user image clicked
            binding.clUserDetails.setOnClickListener {
                callback.onUserImageClicked(item)
            }

            // on list item clicked
            binding.clRepoDetails.setOnClickListener {
                callback.onListItemClicked(item, binding.ivAvatar)
            }
        }

    }
}

class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }
}

