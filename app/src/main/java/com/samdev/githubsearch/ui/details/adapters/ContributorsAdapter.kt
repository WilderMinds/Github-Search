package com.samdev.githubsearch.ui.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samdev.githubsearch.R
import com.samdev.githubsearch.core.domain.Owner
import com.samdev.githubsearch.databinding.ItemContributorsBinding
import com.samdev.githubsearch.extensions.loadUrl
import com.samdev.githubsearch.utils.ItemClickedCallback

class ContributorsAdapter(
    private val callback: ItemClickedCallback
) :
    ListAdapter<Owner, ContributorsAdapter.ItemViewHolder>(OwnerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContributorsBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ItemViewHolder(
        private val binding: ItemContributorsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Owner) = with(itemView) {
            // Set data to your item view here
            binding.ivAvatar.loadUrl(item.avatar_url)
            binding.tvUsername.text = context.getString(R.string.s_username, item.login)

            setOnClickListener {
                onClick(item)
            }
        }

        private fun onClick(item: Owner) {
            callback.onItemClicked(item)
        }
    }
}

class OwnerDiffCallback : DiffUtil.ItemCallback<Owner>() {
    override fun areItemsTheSame(oldItem: Owner, newItem: Owner): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Owner, newItem: Owner): Boolean {
        return oldItem == newItem
    }
}

