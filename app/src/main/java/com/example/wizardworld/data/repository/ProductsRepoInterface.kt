package com.example.wizardworld.data.repository

import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.model.House
import com.example.wizardworld.domain.model.Spell
import com.example.wizardworld.domain.model.Wizard
import kotlinx.coroutines.flow.Flow

interface ProductsRepoInterface {
    fun getWizardList(): Flow<Result<List<Triple<String, String, String>>>>
    fun getWizardById(productId: String): Flow<Result<Wizard>>
    fun getSpellList(): Flow<Result<List<Triple<String, String, String>>>>
    fun getSpellById(productId: String): Flow<Result<Spell>>
    fun getElixirList(): Flow<Result<List<Triple<String, String, String>>>>
    fun getElixirById(productId: String): Flow<Result<Elixir>>
    fun getHouseList(): Flow<Result<List<Triple<String, String, String>>>>
    fun getHouseById(productId: String): Flow<Result<House>>
}