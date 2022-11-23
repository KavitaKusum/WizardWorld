package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.HouseDTO
import com.example.wizardworld.domain.model.House

class HouseListAPiResponseMapper {
    fun toHouseList(response: List<HouseDTO>?): List<Triple<String, String, String>> {
        val list = mutableListOf<Triple<String, String, String>>()
        response?.let {
            for (item in it) {
                list.add(Triple(item.id, item.name, item.founder))
            }
        }
        return list
    }

    fun toHouse(response: HouseDTO?): House {
        return createHouse(response)
    }

    private fun createHouse(item: HouseDTO?): House {
        item?.let {
            val heads = mutableListOf<String>()
            val traits = mutableListOf<String>()
            it.heads?.let { list ->
                for (head in list)
                    heads.add(getHeadFullName(head.firstName, head.lastName))
            }
            it.traits?.let { list ->
                for (trait in list)
                    traits.add(trait.name ?: "")
            }
            return House(
                id = it.id ?: "",
                name = it.name ?: "",
                houseColours = it.houseColours ?: "",
                founder = it.founder ?: "",
                animal = it.animal ?: "",
                element = it.element ?: "",
                ghost = it.ghost ?: "",
                commonRoom = it.commonRoom ?: "",
                heads = heads,
                traits = traits
            )
        }
        return House("", "", "", "", "", "", "", "", listOf(), listOf())
    }

    private fun getHeadFullName(firstName: String?, lastName: String?): String {
        return when {
            firstName == null -> lastName ?: ""
            lastName == null -> firstName
            else -> String.format("%s %s", firstName, lastName)
        }
    }
}
