package com.szczurk3y.repository.presentation.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.szczurk3y.repository.R
import com.szczurk3y.repository.databinding.ItemRepoBinding
import com.szczurk3y.repository.domain.RawRepo

class ReposAdapter(private val onClick: (item: RawRepo) -> Unit) :
    PagingDataAdapter<RawRepo, ReposAdapter.RepoViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RepoViewHolder(
        private val binding: ItemRepoBinding,
        private val onClick: (item: RawRepo) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: RawRepo?) = with(binding) {
            if (repo != null) {
                Glide.with(itemView)
                    .load(repo.owner.avatarUrl)
                    .centerCrop()
                    .apply(RequestOptions.circleCropTransform())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivAuthor)

                tvTitle.text = repo.name
                tvStars.text = repo.stargazersCount.toString()

                root.setOnClickListener {
                    onClick(repo)
                }
            }
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<RawRepo>() {
            override fun areItemsTheSame(oldItem: RawRepo, newItem: RawRepo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RawRepo, newItem: RawRepo) =
                oldItem == newItem
        }
    }
}