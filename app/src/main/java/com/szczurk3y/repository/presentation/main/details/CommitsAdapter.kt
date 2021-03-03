package com.szczurk3y.repository.presentation.main.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.szczurk3y.repository.databinding.ItemCommitBinding
import com.szczurk3y.repository.domain.CommitListItem

class CommitsAdapter: ListAdapter<CommitListItem, CommitsAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position+1)
    }

    inner class ViewHolder(private val binding: ItemCommitBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(commit: CommitListItem, position: Int) {
            binding.apply {
                tvCircleNumber.text = position.toString()
                tvAuthorCommitName.text = commit.author
                tvAuthorEmail.text = commit.authorEmail
                tvCommitMessage.text = commit.message
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object  : DiffUtil.ItemCallback<CommitListItem>() {
            override fun areItemsTheSame(
                oldItem: CommitListItem,
                newItem: CommitListItem
            ): Boolean = oldItem.author == newItem.author

            override fun areContentsTheSame(
                oldItem: CommitListItem,
                newItem: CommitListItem
            ): Boolean = oldItem == newItem
        }
    }
}