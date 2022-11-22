package com.example.wizardworld.domain

sealed class Result<T>(val result: T? = null, val msg: String? = null) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(msg: String) :  Result<T>(null, msg)
    class Loading<T> : Result<T>()
}