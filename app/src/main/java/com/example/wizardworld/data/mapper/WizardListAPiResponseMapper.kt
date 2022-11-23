package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.WizardDTO
import com.example.wizardworld.domain.model.Wizard

class WizardListAPiResponseMapper {
    fun toWizardList(response: List<WizardDTO>?): List<Triple<String, String, String>> {
        val list = mutableListOf<Triple<String, String, String>>()
        response?.let {
            for (item in it) {
                list.add(
                    Triple(
                        item.id,
                        getWizardFullName(item.firstName, item.lastName),
                        if (item.elixirs.isNullOrEmpty()) "0" else item.elixirs.size.toString()
                    )
                )
            }
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