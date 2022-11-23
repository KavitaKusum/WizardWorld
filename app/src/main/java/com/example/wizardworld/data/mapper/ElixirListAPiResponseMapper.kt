package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.ElixirDTO
import com.example.wizardworld.domain.model.Elixir

class ElixirListAPiResponseMapper {
    fun toElixirList(response: List<ElixirDTO>?): List<Elixir> {
        val list = mutableListOf<Elixir>()
        response?.let {
            for (item in it)
                list.add(createElixir(item))
        }
        return list
    }

    fun toElixir(response: ElixirDTO?): Elixir {
        return createElixir(response)
    }

    private fun createElixir(item: ElixirDTO?): Elixir {
        item?.let {
            val ingredientList = mutableListOf<String>()
            val inventorList = mutableListOf<String>()
            it.ingredients?.let { list ->
                for (ingredient in list)
                    ingredientList.add(ingredient.name ?: "")
            }
            it.inventors?.let { list ->
                for (inventor in list)
                    inventorList.add(getInventorFullName(inventor.firstName, inventor.lastName))
            }
            return Elixir(
                it.name ?: "",
                it.id ?: "",
                it.effect ?: "",
                it.sideEffects ?: "",
                it.characteristics ?: "",
                it.time ?: "",
                it.difficulty ?: "",
                "",
                inventorList,
                ingredientList
            )
        }
        return Elixir("", "", "", "", "", "", "", "", listOf(), listOf())
    }

    private fun getInventorFullName(firstName: String?, lastName: String?): String {
        return when {
            firstName == null -> lastName ?: ""
            lastName == null -> firstName
            else -> String.format("%s %s", firstName, lastName)
        }
    }
}
