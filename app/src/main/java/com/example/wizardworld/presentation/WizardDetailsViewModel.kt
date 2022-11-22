package com.example.wizardworld.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.model.Wizard
import com.example.wizardworld.domain.usecase.WizardDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WizardDetailsViewModel @Inject constructor(private val wizardUseCase: WizardDetailUseCase) : ViewModel() {
    private val _viewState = MutableStateFlow(ViewState<Wizard>(isLoading = true))
    val viewState = _viewState.asStateFlow()
    fun getWizardDetails(wizardId:String){
        viewModelScope.launch {
            wizardUseCase.invoke(wizardId).collect {
                when (it) {
                    is Result.Error -> _viewState.value= ViewState(error = it.msg)
                    is Result.Success -> _viewState.value= ViewState(data=it.result)
                    is Result.Loading -> _viewState.value= ViewState(isLoading = true, data = null, error = null)
                }
            }
        }
    }

    fun getElixirList(elixirs: List<Elixir>): List<String> {
        val list = mutableListOf<String>()
        for(item in elixirs)
            list.add(item.name)
        return list
    }
}
