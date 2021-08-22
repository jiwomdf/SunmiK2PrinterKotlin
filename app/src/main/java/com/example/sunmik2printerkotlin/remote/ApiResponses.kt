package com.example.sunmik2printerkotlin.remote

sealed class ApiResponses<out R> {
    data class Success<out T>(val data: T) : ApiResponses<T>()
    data class Error(val errorMessage: String) : ApiResponses<Nothing>()
    object NotAuthorized: ApiResponses<Nothing>()
    object Empty : ApiResponses<Nothing>()
}