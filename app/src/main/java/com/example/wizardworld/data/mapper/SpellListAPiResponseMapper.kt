package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.SpellDTO
import com.example.wizardworld.domain.model.Spell

class SpellListAPiResponseMapper {
    fun toSpellList(response: List<SpellDTO>?): List<Triple<String, String, String>> {
        val list = mutableListOf<Triple<String, String, String>>()
        response?.let {
            for (item in it) {
                list.add(Triple(item.id, item.name, item.incantation))
            }
        }
        return list
    }

    fun toSpell(response: SpellDTO?): Spell {
        return createSpell(response)
    }

    private fun createSpell(item: SpellDTO?): Spell {
        item?.let {
            return Spell(
                name = it.name ?: "",
                incantation = it.incantation ?: "",
                id = it.id ?: "",
                effect = it.effect ?: "",
                canBeVerbal = it.canBeVerbal ?: "",
                type = it.type ?: "",
                light = it.light ?: "",
                creator = it.creator ?: ""
            )
        }
        return Spell("", "", "", "", "", "", "", "")
    }

}
