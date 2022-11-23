package com.example.wizardworld.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wizardworld.R
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.usecase.ElixirListUseCase
import com.example.wizardworld.domain.usecase.HouseListUseCase
import com.example.wizardworld.domain.usecase.SpellListUseCase
import com.example.wizardworld.domain.usecase.WizardListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val wizardListUseCase: WizardListUseCase,
                                               private val spellListUseCase: SpellListUseCase,
                                               private val elixirListUseCase: ElixirListUseCase,
                                               private val houseListUseCase: HouseListUseCase) : ViewModel() {
    private val _viewState = MutableStateFlow(ViewState<List<Triple<String, String, String>>>(isLoading = true))
    val viewState = _viewState.asStateFlow()

    fun getProductList(choice:Int,context: Context){
        viewModelScope.launch {
            when(choice){
                1-> fetchWizardList(context)
                2-> fetchHouseList(context)
                3-> fetchElixirList(context)
                4-> fetchSpellList(context)
            }
        }
    }

    private suspend fun fetchSpellList(context: Context) {
        spellListUseCase.invoke().collect {
            when (it) {
                is Result.Error -> _viewState.value= ViewState(error = it.msg)
                is Result.Success ->
                    it.result?.let{list->
                        _viewState.value= ViewState(data= getContent(4,context,list))
                    }
                is Result.Loading -> _viewState.value= ViewState(isLoading = true, data = null, error = null)
            }
        }
    }

    private suspend fun fetchElixirList(context: Context) {
        elixirListUseCase.invoke().collect {
            when (it) {
                is Result.Error -> _viewState.value= ViewState(error = it.msg)
                is Result.Success ->
                    it.result?.let{list->
                        _viewState.value= ViewState(data=getContent(3,context,list))
                    }
                is Result.Loading -> _viewState.value= ViewState(isLoading = true, data = null, error = null)
            }
        }
    }

    private suspend fun fetchHouseList(context: Context) {
        houseListUseCase.invoke().collect {
            when (it) {
                is Result.Error -> _viewState.value= ViewState(error = it.msg)
                is Result.Success ->
                    it.result?.let{list->
                        _viewState.value= ViewState(data= getContent(2, context,list))
                    }
                is Result.Loading -> _viewState.value= ViewState(isLoading = true, data = null, error = null)
            }
        }
    }

    private suspend fun fetchWizardList(context: Context) {
        wizardListUseCase.invoke().collect {
            when (it) {
                is Result.Error -> _viewState.value= ViewState(error = it.msg)
                is Result.Success ->
                    it.result?.let{list->
                        _viewState.value= ViewState(data= getContent(1,context, list))
                    }
                is Result.Loading -> _viewState.value= ViewState(isLoading = true, data = null, error = null)
            }
        }
    }

    private fun getContent(choice:Int, context: Context,list: List<Triple<String, String, String>>): List<Triple<String, String, String>> {
        val tripleList= mutableListOf<Triple<String,String,String>>()
        for(item in list){
            val str= when(choice){
                1-> when(item.third.toInt()){
                    0 -> context.getString(R.string.no_specialization)
                    1 -> context.getString(R.string.one_specialization)
                    else -> context.getString(R.string.number_specialization,item.third)
                }
                2->context.getString(R.string.founder,item.third)
                3->context.getString(R.string.effect,item.third)
                else->context.getString(R.string.incantation,item.third)
            }
            tripleList.add(Triple(item.first,item.second,str))
        }
        return tripleList
    }

    fun getHeadingText(choice: Int, context: Context): String {
        return when(choice){
            1-> context.getString(R.string.wizards_list)
            2-> context.getString(R.string.houses_list)
            3-> context.getString(R.string.elixirs_list)
            else-> context.getString(R.string.spells_list)
        }
    }
}