package com.szczurk3y.repository.presentation.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.szczurk3y.repository.databinding.RepoLoadStateFooterBinding

class ReposLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ReposLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = RepoLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: RepoLoadStateFooterBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(loadState: LoadState) {
                    binding.apply {
                        btnRetry.setOnClickListener { retry.invoke() }
                        progressBar.isVisible = loadState is LoadState.Loading
                        btnRetry.isVisible = loadState !is LoadState.Loading
                        tvError.isVisible = loadState !is LoadState.Loading
                        tvError.text = if (loadState is LoadState.Error) {
                            loadState.error.localizedMessage
                        } else {
                            tvError.text // default
                        }

                    }
                }
            }
}