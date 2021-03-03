package com.szczurk3y.repository.presentation.main.list

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.szczurk3y.repository.data.RepositoryImpl
import com.szczurk3y.repository.domain.RawRepo
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*


class ListViewModel @ViewModelInject constructor(
    private val repository: RepositoryImpl,
    @Assisted state: SavedStateHandle
): ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

//    private val searchChannel = ConflatedBroadcastChannel<String>()

    val repos: LiveData<PagingData<RawRepo>> = currentQuery.switchMap { queryString ->
        repository.fetchRepos(queryString).cachedIn(viewModelScope)
    }

    fun searchRepos(query: String = DEFAULT_QUERY) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "%20"
        private const val DEFAULT_QUERY = "cats"
    }
}