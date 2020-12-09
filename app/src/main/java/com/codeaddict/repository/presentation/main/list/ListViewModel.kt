package com.codeaddict.repository.presentation.main.list

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.codeaddict.repository.data.RepositoryImpl
import com.codeaddict.repository.domain.RawRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*


class ListViewModel @ViewModelInject constructor(
    private val repository: RepositoryImpl,
    @Assisted state: SavedStateHandle
): ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "%20" // The internet says it's a space in http requests
    }

    private val searchChannel = ConflatedBroadcastChannel<String>()

    val repos: LiveData<PagingData<RawRepo>> = searchChannel.asFlow()
            .debounce(500)
            .flatMapLatest { query ->
                repository.fetchRepos(query)
            }.asLiveData().cachedIn(viewModelScope)

    fun searchRepos(query: String = DEFAULT_QUERY) {
        searchChannel.offer(query)
    }
}