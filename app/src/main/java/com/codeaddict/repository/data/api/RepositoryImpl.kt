package com.codeaddict.repository.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.codeaddict.repository.domain.DetailsResult
import com.codeaddict.repository.domain.RawCommit
import com.codeaddict.repository.domain.RawRepo
import com.codeaddict.repository.presentation.main.list.ReposPagingSource
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(private val repositoriesApi: RepositoriesApi) {

    fun fetchRepos(query: String): LiveData<PagingData<RawRepo>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 200,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ReposPagingSource(repositoriesApi, query) }
        ).liveData
    }

    suspend fun fetchRepoDetails(login: String, repo: String, pageSize: Int = 3): DetailsResult {
        return try {
            val items = repositoriesApi.getRepoDetails(login, repo, pageSize)
            DetailsResult.Success(items)
        } catch (error: Exception) {
            DetailsResult.Error(error.localizedMessage ?: "Connection problem")
        }
    }
}