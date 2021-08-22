package com.example.sunmik2printerkotlin.util

const val DATA_NOT_FOUND = "Data not found"
sealed class Event<out T> {
    class Success<out T>(val data: T): Event<T>()
    class NotFound<out T>(val message: String = DATA_NOT_FOUND) : Event<T>()
    class Error(val message: String = ""): Event<Nothing>()
    object Loading: Event<Nothing>()
}