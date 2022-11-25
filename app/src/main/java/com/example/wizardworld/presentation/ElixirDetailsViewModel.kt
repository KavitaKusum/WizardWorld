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
    private val emptyObject = Elixir()
    private val _viewState = MutableStateFlow<ViewState<Elixir>>(ViewState.Loading(emptyObject))
    val viewState = _viewState.asStateFlow()

    fun getElixirDetails(elixirId: String) {
        viewModelScope.launch {
            elixirUseCase.invoke(elixirId).collect {
                when (it) {
                    is Result.Error -> _viewState.value = ViewState.Error(emptyObject, it.msg ?: "")
                    is Result.Success ->
                        it.result?.let { list ->
                            _viewState.value = ViewState.Success(list)
                        }
                    is Result.Loading -> _viewState.value = ViewState.Loading(emptyObject)
                }
            }
        }
    }
}

