package com.codeaddict.repository.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RawCommit(
    private val sha: String,
    private val commit: Commit,
    private val author: Author
) : Parcelable {

    @Parcelize
    data class Commit(
        private val author: CommitAuthor,
        private val message: String
    ) : Parcelable {

        @Parcelize
        data class CommitAuthor(
            private val name: String,
            private val email: String
        ) : Parcelable

    }

    @Parcelize
    data class Author(
        @Json(name = "login") private val name: String,
        @Json(name = "avatar_url") private val avatarUrl: String
    ) : Parcelable

}