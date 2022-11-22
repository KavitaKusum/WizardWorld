package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.ElixirDTO
import com.example.wizardworld.data.dto.IngredientDTO
import com.example.wizardworld.data.dto.InventorDTO
import com.example.wizardworld.data.dto.WizardDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class WizardListAPiResponseMapperTest : TestCase() {
    private lateinit var mapper:WizardListAPiResponseMapper
    public override fun setUp() {
        super.setUp()
        mapper= WizardListAPiResponseMapper()
    }
    fun testToWizardList() {
        assertEquals(mapper.toWizardList(listOf(getWizardDTO()))[0].name,"Kavita Kusum")
    }

    fun testToWizard() {
        assertEquals(mapper.toWizard(getWizardDTO()).name,"Kavita Kusum")
    }

    private fun getWizardDTO(): WizardDTO {
        val wizard= mockk<WizardDTO>()
        coEvery{wizard.firstName} returns "Kavita"
        coEvery{wizard.lastName} returns "Kusum"
        coEvery{wizard.id} returns "1"
        val elixir = mockk<ElixirDTO>()
        coEvery{elixir.characteristics} returns "characteristics"
        coEvery{elixir.difficulty} returns "Hard"
        coEvery{elixir.id} returns "1"
        coEvery{elixir.effect} returns "effect"
        coEvery{elixir.sideEffects} returns "sideEffects"
        coEvery{elixir.manufacturer} returns "manufacturer"
        coEvery{elixir.time} returns "time"
        coEvery{elixir.name} returns "elixir"
        val inventor=mockk<InventorDTO>()
        coEvery { inventor.id } returns "1"
        coEvery{inventor.firstName} returns "Kavita"
        coEvery{inventor.lastName} returns "Kusum"
        coEvery{elixir.inventors} returns(listOf(inventor))
        val ingredient=mockk<IngredientDTO>()
        coEvery{ingredient.id} returns "1"
        coEvery{ingredient.name} returns "Ingredient"
        coEvery{elixir.ingredients} returns(listOf(ingredient))
        coEvery{wizard.elixirs} returns(listOf(elixir))
        return wizard
    }
}