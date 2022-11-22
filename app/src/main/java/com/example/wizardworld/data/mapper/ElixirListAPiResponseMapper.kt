package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.ElixirDTO
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.model.Ingredient
import com.example.wizardworld.domain.model.Inventor

class ElixirListAPiResponseMapper {
    fun toElixirList(response: List<ElixirDTO>?): List<Elixir> {
        val list= mutableListOf<Elixir>()
        for(item in response!!)
            list.add(createElixir(item))
        return list
    }

    fun toElixir(response: ElixirDTO): Elixir {
        return createElixir(response)
    }

    private fun createElixir(item: ElixirDTO):Elixir{
        val ingredientList= mutableListOf<Ingredient>()
        val inventorList = mutableListOf<Inventor>()
        for (ingre in item.ingredients!!)
            ingredientList.add(Ingredient(ingre.id ?: "",ingre.name ?: ""))
        for (inventor in item.inventors!!)
            inventorList.add(Inventor(inventor.id ?: "", getInventorFullName(inventor.firstName, inventor.lastName)))
        return Elixir(item.name ?: "", item.id ?: "", item.effect ?: "", item.sideEffects ?: "",
            item.characteristics ?: "", item.time ?: "", item.difficulty ?: "", ingredientList, inventorList)
    }
    private fun getInventorFullName(firstName: String?, lastName: String?): String {
        return if(firstName==null && lastName==null)
            "Anonymous"
        else if (firstName== null)
            lastName!!
        else if(lastName==null)
            firstName
        else String.format("%s %s", firstName, lastName)
    }
}
