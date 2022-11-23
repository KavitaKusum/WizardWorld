package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.*
import com.example.wizardworld.data.dto.ElixirDTO
import com.example.wizardworld.data.dto.IngredientDTO
import com.example.wizardworld.data.dto.InventorDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class ElixirListAPiResponseMapperTest : TestCase() {
    private lateinit var elixirListAPiResponseMapper: ElixirListAPiResponseMapper
    public override fun setUp() {
        super.setUp()
        elixirListAPiResponseMapper = ElixirListAPiResponseMapper()
    }

    fun testToElixirList() {
        assertEquals(
            elixirListAPiResponseMapper.toElixirList(listOf(getElixirDTO()))[0].characteristics,
            characteristics
        )
    }

    fun testToElixir() {
        assertEquals(elixirListAPiResponseMapper.toElixir(getElixirDTO()).name, elixir)
    }

    private fun getElixirDTO(): ElixirDTO {
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
        return elixirObj
    }
}