package com.codeaddict.repository.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RawCommit(
    val sha: String,
    val commit: Commit,
    val author: Author?
) : Parcelable {

    @Parcelize
    data class Commit(
        val author: CommitAuthor?,
        val message: String?
    ) : Parcelable {

        @Parcelize
        data class CommitAuthor(
            val name: String,
            val email: String
        ) : Parcelable

    }

    @Parcelize
    data class Author(
        @Json(name = "login") val name: String,
        @Json(name = "avatar_url") val avatarUrl: String
    ) : Parcelable

}