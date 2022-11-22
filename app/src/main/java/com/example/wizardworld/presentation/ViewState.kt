package com.example.wizardworld.presentation
data class ViewState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null
)
