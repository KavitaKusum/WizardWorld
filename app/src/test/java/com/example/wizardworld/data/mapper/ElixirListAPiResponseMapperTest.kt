package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.ElixirDTO
import com.example.wizardworld.data.dto.IngredientDTO
import com.example.wizardworld.data.dto.InventorDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class ElixirListAPiResponseMapperTest : TestCase() {
    private lateinit var elixirListAPiResponseMapper:ElixirListAPiResponseMapper
    public override fun setUp() {
        super.setUp()
        elixirListAPiResponseMapper= ElixirListAPiResponseMapper()
    }
    fun testToElixirList() {
        assertEquals(elixirListAPiResponseMapper.toElixirList(listOf(getElixirDTO()))[0].characteristics,"characteristics")
    }

    fun testToElixir() {
        assertEquals(elixirListAPiResponseMapper.toElixir(getElixirDTO()).name,"elixir")
    }

    private fun getElixirDTO(): ElixirDTO {
        val elixir= mockk<ElixirDTO>()
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
        return elixir
    }
}