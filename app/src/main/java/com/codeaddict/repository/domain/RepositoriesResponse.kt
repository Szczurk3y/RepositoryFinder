package com.codeaddict.repository.domain

import com.codeaddict.repository.domain.RawRepo
import com.squareup.moshi.Json

data class RepositoriesResponse(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
    @Json(name = "items") val items: List<RawRepo>
)