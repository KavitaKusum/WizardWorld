package com.example.wizardworld.presentation

sealed class ViewState<T>(val result: T, val msg: String, val isLoading: Boolean) {
    class Success<T>(data: T) : ViewState<T>(data, "", false)
    class Error<T>(result: T, msg: String) : ViewState<T>(result, msg, false)
    class Loading<T>(result: T) : ViewState<T>(result, "", true)
}