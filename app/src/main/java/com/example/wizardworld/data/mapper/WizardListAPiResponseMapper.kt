package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.WizardDTO
import com.example.wizardworld.domain.model.Wizard

class WizardListAPiResponseMapper {
    fun toWizardList(response: List<WizardDTO>?): List<Wizard> {
        val list = mutableListOf<Wizard>()
        response?.let {
            for (item in it)
                list.add(createWizard(item))
        }
        return list
    }

    fun toWizard(response: WizardDTO?): Wizard {
        return createWizard(response)
    }

    private fun createWizard(item: WizardDTO?): Wizard {
        item?.let {
            val elixirList = mutableListOf<String>()
            it.elixirs?.let { list ->
                for (elixirItem in list)
                    elixirList.add(elixirItem.name)
            }
            return Wizard(
                getWizardFullName(item.firstName, item.lastName),
                item.id ?: "",
                elixirList
            )
        }
        return Wizard("", "", listOf())
    }

    private fun getWizardFullName(firstName: String?, lastName: String?): String {
        return when {
            firstName == null -> lastName ?: ""
            lastName == null -> firstName
            else -> String.format("%s %s", firstName, lastName)
        }
    }
}