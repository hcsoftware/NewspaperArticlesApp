package com.xr6software.theguardiannews.repositories.model

sealed class RepositoryStatus <out T> {
    data class Success<out T>(val data: T) : RepositoryStatus<T>()
    data class Failed(val message: String): RepositoryStatus<Nothing>()
}