package com.example.wizardworld.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wizardworld.R
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.model.House
import com.example.wizardworld.domain.model.Spell
import com.example.wizardworld.domain.model.Wizard
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

    fun getProductList(choice:Int, context: Context){
        when(choice){
            1-> fetchWizardList(context)
            2-> fetchHouseList(context)
            3-> fetchElixirList(context)
            4-> fetchSpellList(context)
        }
    }

    private fun fetchSpellList(context: Context) {
        viewModelScope.launch {
            spellListUseCase.invoke().collect {
                when (it) {
                    is Result.Error -> _viewState.value= ViewState(error = it.msg)
                    is Result.Success ->
                        it.result?.let{list->
                            _viewState.value= ViewState(data= transformSpellList(list, context))
                            }
                    is Result.Loading -> _viewState.value= ViewState(isLoading = true, data = null, error = null)
                }
            }
        }
    }

    private fun fetchElixirList(context: Context) {
        viewModelScope.launch {
            elixirListUseCase.invoke().collect {
                when (it) {
                    is Result.Error -> _viewState.value= ViewState(error = it.msg)
                    is Result.Success ->
                        it.result?.let{list->
                            _viewState.value= ViewState(data= transformElixirList(list, context))
                             }
                    is Result.Loading -> _viewState.value= ViewState(isLoading = true, data = null, error = null)
                }
            }
        }
    }

    private fun fetchHouseList(context: Context) {
        viewModelScope.launch {
            houseListUseCase.invoke().collect {
                when (it) {
                    is Result.Error -> _viewState.value= ViewState(error = it.msg)
                    is Result.Success ->
                        it.result?.let{list->
                            _viewState.value= ViewState(data= transformHouseList(list, context))
                            }
                    is Result.Loading -> _viewState.value= ViewState(isLoading = true, data = null, error = null)
                }
            }
        }
    }

    private fun fetchWizardList(context: Context) {
        viewModelScope.launch {
            wizardListUseCase.invoke().collect {
                when (it) {
                    is Result.Error -> _viewState.value= ViewState(error = it.msg)
                    is Result.Success ->
                        it.result?.let{list->
                            _viewState.value= ViewState(data= transformWizardList(list, context))
                            }
                    is Result.Loading -> _viewState.value= ViewState(isLoading = true, data = null, error = null)
                }
            }
        }
    }

    private fun transformWizardList(wizardList: List<Wizard>, context: Context): List<Triple<String,String,String>> {
        val list = mutableListOf<Triple<String,String,String>>()
        var triple:Triple<String,String,String>
        for(item in wizardList) {
            triple = Triple(item.id,
                context.getString(R.string.name, item.name),
                getElixirText(context,if(item.elixirs.isNullOrEmpty())0 else item.elixirs.size)
            )
            list.add(triple)
        }
        return list
    }

    private fun transformHouseList(houseList: List<House>, context: Context): List<Triple<String,String,String>>{
        val list = mutableListOf<Triple<String,String,String>>()
        var triple:Triple<String,String,String>
        for(item in houseList) {
            triple = Triple(item.id, context.getString(R.string.name, item.name), context.getString(R.string.founder, item.founder))
            list.add(triple)
        }
        return list
    }

    private fun transformElixirList(elixirList: List<Elixir>, context: Context): List<Triple<String,String,String>>{
        val list = mutableListOf<Triple<String,String,String>>()
        var triple:Triple<String,String,String>
        for(item in elixirList) {
            triple = Triple(item.id,
                context.getString(R.string.name, item.name),
                context.getString(R.string.effect, item.effect))
            list.add(triple)
        }
        return list
    }

    private fun transformSpellList(spellList: List<Spell>, context: Context): List<Triple<String,String,String>> {
        val list = mutableListOf<Triple<String,String,String>>()
        var triple:Triple<String,String,String>
        for(item in  spellList) {
            triple = Triple(item.id, context.getString(R.string.name, item.name),
                context.getString(R.string.incantation, item.incantation))
            list.add(triple)
        }
        return list
    }

    private fun getElixirText(context: Context, size:Int): String {
        return when (size) {
            0 -> context.getString(R.string.no_specialization)
            1 -> context.getString(R.string.one_specialization)
            else -> context.getString(R.string.number_specialization,size)
        }
    }

    fun getHeadingText(choice: Int, context: Context): String {
        return when(choice){
            1-> context.getString(R.string.wizards_list)
            2-> context.getString(R.string.houses_list)
            3-> context.getString(R.string.elixirs_list)
            4-> context.getString(R.string.spells_list)
            else-> ""
        }
    }
}