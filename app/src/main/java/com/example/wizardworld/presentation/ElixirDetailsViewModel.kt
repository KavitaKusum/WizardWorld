package com.example.wizardworld.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.usecase.ElixirDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElixirDetailsViewModel @Inject constructor(private val elixirUseCase: ElixirDetailUseCase) :
    ViewModel() {
    private val _viewState = MutableStateFlow(ViewState<Elixir>(isLoading = true))
    val viewState = _viewState.asStateFlow()

    fun getElixirDetails(elixirId: String) {
        viewModelScope.launch {
            elixirUseCase.invoke(elixirId).collect {
                when (it) {
                    is Result.Error -> _viewState.value =
                        ViewState(isLoading = false, data = null, error = it.msg)
                    is Result.Success -> _viewState.value =
                        ViewState(isLoading = false, data = it.result, error = null)
                    is Result.Loading -> _viewState.value =
                        ViewState(isLoading = true, data = null, error = null)
                }
            }
        }
    }
}

