package com.szczurk3y.repository.data.api

import com.szczurk3y.repository.domain.RawRepo
import com.squareup.moshi.Json

data class RepositoriesResponse(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
    @Json(name = "items") val items: List<RawRepo>
)