package com.codeaddict.repository.domain

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.squareup.moshi.Json


@Parcelize
data class RawRepo(
    val id: Int,
    val name: String,
    @Json(name = "full_name") val fullName: String,
    val private: Boolean,
    val owner: Owner,
    @Json(name = "stargazers_count") val stargazersCount: Int
) : Parcelable {
    @Parcelize
    data class Owner(
        val login: String,
        val id: Int,
        @Json(name = "avatar_url") val avatarUrl: String
    ) : Parcelable
}