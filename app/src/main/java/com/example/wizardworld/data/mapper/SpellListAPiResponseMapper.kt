package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.SpellDTO
import com.example.wizardworld.domain.model.Spell

class SpellListAPiResponseMapper {
    fun toSpellList(response: List<SpellDTO>?): List<Spell> {
        val list= mutableListOf<Spell>()
        for(item in response!!)
            list.add(createSpell(item))
        return list
    }

    fun toSpell(response: SpellDTO): Spell {
        return createSpell(response)
    }

    private fun createSpell(item: SpellDTO): Spell {
        return Spell(name = item.name ?: "", incantation = item.incantation ?: "", id= item.id ?: "", effect = item.effect ?: "",
            canBeVerbal = item.canBeVerbal ?: "", type =item.type ?: "", light = item.light ?: "", creator = item.creator ?: "")
    }

}
