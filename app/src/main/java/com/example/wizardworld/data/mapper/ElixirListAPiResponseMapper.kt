package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.ElixirDTO
import com.example.wizardworld.domain.model.Elixir

class ElixirListAPiResponseMapper {
    fun toElixirList(response: List<ElixirDTO>?): List<Triple<String, String, String>> {
        val list = mutableListOf<Triple<String, String, String>>()
        response?.let {
            for (item in it)
                list.add(Triple(item.id?:"", item.name?:"", item.effect?:""))
        }
        return list
    }

    fun toElixir(response: ElixirDTO?): Elixir {
        var elixir = Elixir()
        response?.let {
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
            elixir = Elixir(
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
        return elixir
    }

    private fun getInventorFullName(firstName: String?, lastName: String?): String {
        return when {
            firstName == null -> lastName ?: ""
            lastName == null -> firstName
            else -> String.format("%s %s", firstName, lastName)
        }
    }
}
