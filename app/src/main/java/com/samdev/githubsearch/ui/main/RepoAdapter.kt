package com.samdev.githubsearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samdev.githubsearch.data.models.Repo
import com.samdev.githubsearch.databinding.ItemRepositoryBinding

class RepoAdapter : ListAdapter<Repo, RepoAdapter.ItemViewHolder>(RepoDiffCallback()) {

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
            binding.tvAuthorName.text = item.owner?.login
            binding.tvRepoName.text = item.fullName
            binding.tvDescription.text = item.description
            binding.tvWatchers.text = "${item.watchersCount ?: 0}"
            binding.tvForks.text = "${item.forksCount ?: 0}"
            binding.tvIssues.text = "${item.openIssuesCount ?: 0}"

            setOnClickListener {
                onClick(item)
            }
        }

        private fun onClick(item: Repo) {

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

