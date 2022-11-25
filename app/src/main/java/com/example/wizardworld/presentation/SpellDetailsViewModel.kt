package com.example.wizardworld.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Spell
import com.example.wizardworld.domain.usecase.SpellDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpellDetailsViewModel @Inject constructor(private val spellUseCase: SpellDetailsUseCase) :
    ViewModel() {
    private val emptyObject = Spell()
    private val _viewState = MutableStateFlow<ViewState<Spell>>(ViewState.Loading(emptyObject))
    val viewState = _viewState.asStateFlow()
    fun getSpellDetails(spellId: String) {
        viewModelScope.launch {
            spellUseCase.invoke(spellId).collect {
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

