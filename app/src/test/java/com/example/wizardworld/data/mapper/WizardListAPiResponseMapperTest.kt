package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.*
import com.example.wizardworld.data.dto.ElixirDTO
import com.example.wizardworld.data.dto.IngredientDTO
import com.example.wizardworld.data.dto.InventorDTO
import com.example.wizardworld.data.dto.WizardDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class WizardListAPiResponseMapperTest : TestCase() {
    private lateinit var mapper: WizardListAPiResponseMapper
    public override fun setUp() {
        super.setUp()
        mapper = WizardListAPiResponseMapper()
    }

    fun testToWizardList() {
        assertEquals(
            mapper.toWizardList(listOf(getWizardDTO()))[0].name,
            String.format("%s %s", wizard, elixir)
        )
    }

    fun testToWizard() {
        assertEquals(mapper.toWizard(getWizardDTO()).id, id)
    }

    private fun getWizardDTO(): WizardDTO {
        val wizardObj = mockk<WizardDTO>()
        coEvery { wizardObj.firstName } returns wizard
        coEvery { wizardObj.lastName } returns elixir
        coEvery { wizardObj.id } returns id
        val elixirObj = mockk<ElixirDTO>()
        coEvery { elixirObj.name } returns elixir
        coEvery { elixirObj.id } returns id
        coEvery { elixirObj.effect } returns effect
        coEvery { elixirObj.sideEffects } returns ""
        coEvery { elixirObj.characteristics } returns characteristics
        coEvery { elixirObj.time } returns ""
        coEvery { elixirObj.difficulty } returns ""
        val ingredientObj = mockk<IngredientDTO>()
        coEvery { ingredientObj.name } returns ingredient
        coEvery { elixirObj.ingredients } returns (listOf(ingredientObj))
        val inventorObj = mockk<InventorDTO>()
        coEvery { inventorObj.firstName } returns inventor
        coEvery { inventorObj.lastName } returns ""
        coEvery { elixirObj.inventors } returns (listOf(inventorObj))
        coEvery { elixirObj.manufacturer } returns ""
        coEvery { wizardObj.elixirs } returns (listOf(elixirObj))
        return wizardObj
    }
}