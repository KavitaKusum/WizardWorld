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
class SpellDetailsViewModel @Inject constructor(private val spellUseCase: SpellDetailsUseCase) : ViewModel() {
    private val _viewState = MutableStateFlow(ViewState<Spell>(isLoading = true))
    val viewState = _viewState.asStateFlow()
    fun getSpellDetails(spellId:String) {
        viewModelScope.launch {
            spellUseCase.invoke(spellId).collect {
                when (it) {
                    is Result.Error -> _viewState.value= ViewState(error = it.msg)
                    is Result.Success -> _viewState.value= ViewState(data=it.result)
                    is Result.Loading -> _viewState.value= ViewState(isLoading = true, data = null, error = null)
                }
            }
        }
    }
}

