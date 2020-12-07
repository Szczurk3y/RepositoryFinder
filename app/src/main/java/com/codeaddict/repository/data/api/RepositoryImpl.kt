package com.codeaddict.repository.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.codeaddict.repository.data.api.RepositoriesApi
import com.codeaddict.repository.domain.RawRepo
import com.codeaddict.repository.presentation.main.list.ReposPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(private val repositoriesApi: RepositoriesApi) {

    fun fetchRepos(query: String): LiveData<PagingData<RawRepo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 40,
                maxSize = 200,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ReposPagingSource(repositoriesApi, query) }
        ).liveData
    }
}