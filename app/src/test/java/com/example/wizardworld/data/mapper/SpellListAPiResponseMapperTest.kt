package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.*
import com.example.wizardworld.data.dto.SpellDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class SpellListAPiResponseMapperTest : TestCase() {

    private lateinit var mapper: SpellListAPiResponseMapper
    public override fun setUp() {
        super.setUp()
        mapper = SpellListAPiResponseMapper()
    }

    fun testToSpellList() {
        assertEquals(mapper.toSpellList(listOf(getSpellDTO()))[0].first, id)
    }

    fun testToSpell() {
        assertEquals(mapper.toSpell(getSpellDTO()).creator, creator)
    }

    private fun getSpellDTO(): SpellDTO {
        val spellObj = mockk<SpellDTO>()
        coEvery { spellObj.name } returns spell
        coEvery { spellObj.incantation } returns incantation
        coEvery { spellObj.id } returns id
        coEvery { spellObj.effect } returns effect
        coEvery { spellObj.canBeVerbal } returns ""
        coEvery { spellObj.type } returns ""
        coEvery { spellObj.light } returns ""
        coEvery { spellObj.creator } returns creator
        return spellObj
    }
}