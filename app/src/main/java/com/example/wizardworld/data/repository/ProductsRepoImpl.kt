package com.example.wizardworld.data.repository

import com.example.wizardworld.data.api.WizardService
import com.example.wizardworld.data.mapper.ElixirListAPiResponseMapper
import com.example.wizardworld.data.mapper.HouseListAPiResponseMapper
import com.example.wizardworld.data.mapper.SpellListAPiResponseMapper
import com.example.wizardworld.data.mapper.WizardListAPiResponseMapper
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.model.House
import com.example.wizardworld.domain.model.Spell
import com.example.wizardworld.domain.model.Wizard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductsRepoImpl @Inject constructor(
    private val wizardService: WizardService,
    private val wizardMapper: WizardListAPiResponseMapper,
    private val spellMapper: SpellListAPiResponseMapper,
    private val elixirMapper: ElixirListAPiResponseMapper,
    private val houseMapper: HouseListAPiResponseMapper
) : ProductsRepoInterface {

    private var localList: List<Triple<String, String, String>> = listOf()

    override fun getWizardList(): Flow<Result<List<Triple<String, String, String>>>> =
        flow {
            if (localList.isEmpty()) {
                val response = wizardService.getWizards()
                if (response.isSuccessful) {
                    val list = wizardMapper.toWizardList(response.body())
                    localList = list
                    emit(Result.Success(list))
                } else
                    emit(Result.Error(response.message()))
            } else
                emit(Result.Success(localList))
        }.flowOn(Dispatchers.IO)

    override fun getWizardById(productId: String): Flow<Result<Wizard>> =
        flow {
            val response = wizardService.getWizardById(productId)
            if (response.isSuccessful)
                emit(Result.Success(wizardMapper.toWizard(response.body())))
            else
                emit(Result.Error(response.message()))
        }.flowOn(Dispatchers.IO)

    override fun getSpellList(): Flow<Result<List<Triple<String, String, String>>>> =
        flow {
            if (localList.isEmpty()) {
                val response = wizardService.getSpells()
                if (response.isSuccessful) {
                    val list = spellMapper.toSpellList(response.body())
                    localList = list
                    emit(Result.Success(list))
                } else
                    emit(Result.Error(response.message()))
            } else
                emit(Result.Success(localList))
        }.flowOn(Dispatchers.IO)

    override fun getSpellById(productId: String): Flow<Result<Spell>> =
        flow {
            val response = wizardService.getSpellById(productId)
            if (response.isSuccessful)
                emit(Result.Success(spellMapper.toSpell(response.body())))
            else
                emit(Result.Error(response.message()))
        }.flowOn(Dispatchers.IO)

    override fun getElixirList(): Flow<Result<List<Triple<String, String, String>>>> =
        flow {
            if (localList.isEmpty()) {
                val response = wizardService.getElixirs()
                if (response.isSuccessful) {
                    val list = elixirMapper.toElixirList((response.body()))
                    localList = list
                    emit(Result.Success(list))
                } else
                    emit(Result.Error(response.message()))
            } else
                emit(Result.Success(localList))
        }.flowOn(Dispatchers.IO)

    override fun getElixirById(productId: String): Flow<Result<Elixir>> =
        flow {
            val response = wizardService.getElixirById(productId)
            if (response.isSuccessful)
                emit(Result.Success(elixirMapper.toElixir(response.body())))
            else
                emit(Result.Error(response.message()))
        }.flowOn(Dispatchers.IO)

    override fun getHouseList(): Flow<Result<List<Triple<String, String, String>>>> =
        flow {
            if (localList.isEmpty()) {
                val response = wizardService.getHouses()
                if (response.isSuccessful) {
                    val list = houseMapper.toHouseList(response.body())
                    localList = list
                    emit(Result.Success(list))
                } else
                    emit(Result.Error(response.message()))
            } else emit(Result.Success(localList))
        }.flowOn(Dispatchers.IO)

    override fun getHouseById(productId: String): Flow<Result<House>> =
        flow {
            val response = wizardService.getHouseById(productId)
            if (response.isSuccessful)
                emit(Result.Success(houseMapper.toHouse(response.body())))
            else
                emit(Result.Error(response.message()))
        }.flowOn(Dispatchers.IO)
}