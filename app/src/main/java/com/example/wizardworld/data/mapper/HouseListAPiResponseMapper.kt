package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.HouseDTO
import com.example.wizardworld.domain.model.Heads
import com.example.wizardworld.domain.model.House
import com.example.wizardworld.domain.model.Traits

class HouseListAPiResponseMapper {
    fun toHouseList(response: List<HouseDTO>?): List<House> {
        val list= mutableListOf<House>()
        for(item in response!!)
            list.add(createHouse(item))
        return list
    }

    fun toHouse(response: HouseDTO): House {
        return createHouse(response)
    }

    private fun createHouse(item: HouseDTO): House {
        val heads=mutableListOf<Heads>()
        val traits = mutableListOf<Traits>()
        if(!item.heads.isNullOrEmpty())
            for (head in item.heads)
                heads.add(Heads(head.id ?: "", getHeadFullName(head.firstName,head.lastName)))
        if(!item.traits.isNullOrEmpty())
            for (trait in item.traits)
                traits.add(Traits(trait.id ?: "",trait.name ?: ""))
        return House(id= item.id ?: "", name = item.name ?: "", houseColours = item.houseColours ?: "", founder = item.founder ?: "",
            animal = item.animal ?: "", element = item.element ?: "", ghost = item.ghost ?: "", commonRoom = item.commonRoom ?: "",
            heads = heads, traits = traits )
    }
    private fun getHeadFullName(firstName: String?, lastName: String?): String {
        return if(firstName==null && lastName==null)
            "Anonymous"
        else if (firstName== null)
            lastName!!
        else if(lastName==null)
            firstName
        else String.format("%s %s", firstName, lastName)
    }
}
