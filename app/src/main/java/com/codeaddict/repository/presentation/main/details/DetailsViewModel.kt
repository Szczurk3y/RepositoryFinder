package com.codeaddict.repository.presentation.main.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeaddict.repository.data.RepositoryImpl
import com.codeaddict.repository.domain.CommitListItem
import com.codeaddict.repository.domain.DetailsResult
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val repository: RepositoryImpl,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    val commitsLiveData: MutableLiveData<List<CommitListItem>> = MutableLiveData()
    val errorsLiveData: MutableLiveData<String> = MutableLiveData()

    fun getRepoDetails(login: String, repo: String) {
        viewModelScope.launch {
            kotlin.runCatching { repository.fetchRepoDetails(login, repo) }
                .onSuccess { result ->
                    when(result) {
                        is DetailsResult.Success -> {
                            commitsLiveData.value = result.items
                        }
                        is DetailsResult.Error -> {
                            errorsLiveData.postValue(result.message)
                        }
                    }
                }.onFailure { error ->
                    errorsLiveData.postValue(error.localizedMessage)
                }
        }
    }

}