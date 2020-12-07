package com.codeaddict.repository.data.api

import com.codeaddict.repository.domain.RawRepo
import com.codeaddict.repository.domain.RepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
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

//    @Headers("Content-Type:application/json")
//    @GET("repos")

}