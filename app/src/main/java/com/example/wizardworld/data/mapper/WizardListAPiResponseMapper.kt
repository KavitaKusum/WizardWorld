package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.WizardDTO
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.model.Wizard

class WizardListAPiResponseMapper {
    fun toWizardList(response: List<WizardDTO>?): List<Wizard> {
        val list= mutableListOf<Wizard>()
        for(item in response!!)
            list.add(createWizard(item))
        return list
    }

    fun toWizard(response: WizardDTO): Wizard {
        return createWizard(response)
    }

    private fun createWizard(item: WizardDTO): Wizard {
        val elixirList=mutableListOf<Elixir>()
        for(elixirItem in item.elixirs!!)
            elixirList.add(Elixir(elixirItem.name ?: "",elixirItem.id ?: "","","","","","", listOf(), listOf()))
        return Wizard(getWizardFullName(item.firstName,item.lastName) , item.id ?: "", elixirList)
    }

    private fun getWizardFullName(firstName: String?, lastName: String?): String {
        return if(firstName==null && lastName==null)
            "Anonymous"
        else if (firstName== null)
            lastName!!
        else if(lastName==null)
            firstName
        else String.format("%s %s", firstName, lastName)
    }
}