package com.codeaddict.repository.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommitListItem(
    val author: String,
    val authorEmail: String,
    val message: String
) : Parcelable