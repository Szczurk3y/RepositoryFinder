package com.szczurk3y.repository.domain

sealed class DetailsResult {
    class Error(val message: String): DetailsResult()
    class Success(val items: List<CommitListItem>): DetailsResult()
}