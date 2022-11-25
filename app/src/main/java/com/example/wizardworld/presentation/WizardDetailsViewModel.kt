package com.example.wizardworld.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Wizard
import com.example.wizardworld.domain.usecase.WizardDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WizardDetailsViewModel @Inject constructor(private val wizardUseCase: WizardDetailUseCase) :
    ViewModel() {
    private val emptyObject = Wizard()
    private val _viewState = MutableStateFlow<ViewState<Wizard>>(ViewState.Loading(emptyObject))
    val viewState = _viewState.asStateFlow()
    fun getWizardDetails(wizardId: String) {
        viewModelScope.launch {
            wizardUseCase.invoke(wizardId).collect {
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
