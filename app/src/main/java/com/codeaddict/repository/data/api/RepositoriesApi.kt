package com.codeaddict.repository.data.api

import com.codeaddict.repository.domain.RawCommit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoriesApi {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @Headers("Content-Type:application/json")
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): RepositoriesResponse

    @Headers("Content-Type:application/json")
    @GET("repos/{login}/{repo}/commits")
    suspend fun getRepoDetails(
        @Path("login") login: String,
        @Path("repo") repo: String,
        @Query("per_page") perPage: Int
    ): List<RawCommit>

}