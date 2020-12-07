package com.codeaddict.repository.presentation.main.list

import android.util.Log
import androidx.paging.PagingSource
import com.codeaddict.repository.data.api.RepositoriesApi
import com.codeaddict.repository.domain.RawRepo
import retrofit2.HttpException
import java.io.IOException

private const val REPOSITORIES_STARTING_PAGE_INDEX = 1

class ReposPagingSource(
    private val repositoriesApi: RepositoriesApi,
    private val query: String
) : PagingSource<Int, RawRepo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RawRepo> {
        val page = params.key ?: REPOSITORIES_STARTING_PAGE_INDEX

        return try {
            val response = repositoriesApi.searchRepos(query, page, params.loadSize)
            val repos = response.items

            Log.i("DebugError", repos.toString())

            LoadResult.Page(
                data = repos,
                prevKey = if (page == REPOSITORIES_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (repos.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}