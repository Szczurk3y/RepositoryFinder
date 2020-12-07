package com.codeaddict.repository.presentation.main.list

import android.util.Log
import androidx.hilt.Assisted
import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.codeaddict.repository.data.api.RepositoryImpl


class ListViewModel @ViewModelInject constructor(
    private val repository: RepositoryImpl,
    @Assisted state: SavedStateHandle
): ViewModel() {
    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "%20"
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val repos = currentQuery.switchMap { queryString ->
        repository.fetchRepos(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }
}