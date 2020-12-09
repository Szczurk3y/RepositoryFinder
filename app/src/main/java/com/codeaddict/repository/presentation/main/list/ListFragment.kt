package com.codeaddict.repository.presentation.main.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.codeaddict.repository.R
import com.codeaddict.repository.data.api.RepositoriesApi
import com.codeaddict.repository.databinding.FragmentListBinding
import com.codeaddict.repository.framework.navigation.INavigator
import com.codeaddict.repository.framework.navigation.RouteDestination
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

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
            searchInclude.search.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                    charSequence?.let { query ->
                        if (query.isEmpty()) viewmodel.searchRepos()
                        else viewmodel.searchRepos(query.toString())
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }

        viewmodel.searchRepos()

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