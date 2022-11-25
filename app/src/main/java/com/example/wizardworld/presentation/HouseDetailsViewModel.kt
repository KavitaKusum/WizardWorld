package com.example.wizardworld.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.House
import com.example.wizardworld.domain.usecase.HouseDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseDetailsViewModel @Inject constructor(private val houseUseCase: HouseDetailUseCase) :
    ViewModel() {
    private val emptyObject = House()
    private val _viewState = MutableStateFlow<ViewState<House>>(ViewState.Loading(emptyObject))
    val viewState = _viewState.asStateFlow()

    fun getHouseDetails(houseId: String) {
        viewModelScope.launch {
            houseUseCase.invoke(houseId).collect {
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
