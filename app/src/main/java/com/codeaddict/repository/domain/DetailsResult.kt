package com.codeaddict.repository.domain

sealed class DetailsResult {
    class Error(val message: String): DetailsResult()
    class Success(val items: List<RawCommit>): DetailsResult()
}