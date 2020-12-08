package com.codeaddict.repository.presentation.main.list

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codeaddict.repository.data.api.RepositoryImpl
import com.codeaddict.repository.domain.RawRepo
import kotlinx.coroutines.launch


class ListViewModel @ViewModelInject constructor(
    private val repository: RepositoryImpl,
    @Assisted state: SavedStateHandle
): ViewModel() {
    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "%20"
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val repos: LiveData<PagingData<RawRepo>> = currentQuery.switchMap { queryString ->
        repository.fetchRepos(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }
}