package com.codeaddict.repository.presentation.main.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.codeaddict.repository.R
import com.codeaddict.repository.databinding.FragmentListBinding
import com.codeaddict.repository.framework.navigation.INavigator
import com.codeaddict.repository.framework.navigation.RouteDestination
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    @Inject lateinit var navigator: INavigator

    private val viewmodel by viewModels<ListViewModel>()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        _binding = FragmentListBinding.bind(view)

        val adapter = ReposAdapter { repo ->
            navigator.goTo(RouteDestination.Content.DetailsFragment, bundleOf("repo" to repo))
        }

        binding.apply {
            rvReposList.setHasFixedSize(true)
            rvReposList.itemAnimator = null
            rvReposList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { adapter.retry() },
                footer = ReposLoadStateAdapter { adapter.retry() }
            )
            btnRetry.setOnClickListener { adapter.retry() }
        }

        viewmodel.repos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvReposList.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvReposList.isVisible = false
                    tvEmpty.isVisible = true
                } else if (loadState.source.refresh is LoadState.Error) {
                    tvError.isVisible = true
                    tvError.text = (loadState.source.refresh as LoadState.Error).error.localizedMessage
                }

                else {
                    tvEmpty.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}