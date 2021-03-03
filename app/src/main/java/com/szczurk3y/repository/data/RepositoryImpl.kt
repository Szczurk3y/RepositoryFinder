package com.szczurk3y.repository.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.szczurk3y.repository.data.api.RepositoriesApi
import com.szczurk3y.repository.domain.CommitListItem
import com.szczurk3y.repository.domain.DetailsResult
import com.szczurk3y.repository.domain.RawRepo
import com.szczurk3y.repository.presentation.main.list.ReposPagingSource
import kotlinx.coroutines.flow.Flow
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
            val items = repositoriesApi.getRepoDetails(login, repo, pageSize).map { raw_commit ->
                CommitListItem(
                    author = raw_commit.author?.name ?: "Anonymous",
                    authorEmail = raw_commit.commit.author?.email ?: "no@email.com",
                    message = raw_commit.commit.message ?: "Commit without description."
                )
            }
            DetailsResult.Success(items)
        } catch (error: Exception) {
            DetailsResult.Error(error.localizedMessage ?: "Connection problem")
        }
    }
}