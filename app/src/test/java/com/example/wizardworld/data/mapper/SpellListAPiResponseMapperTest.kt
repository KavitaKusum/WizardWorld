package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.SpellDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class SpellListAPiResponseMapperTest : TestCase() {

    private lateinit var mapper:SpellListAPiResponseMapper
    public override fun setUp() {
        super.setUp()
        mapper= SpellListAPiResponseMapper()
    }
    fun testToSpellList() {
        assertEquals(mapper.toSpellList(listOf(getSpellDTO()))[0].name,"Kavita")
    }

    fun testToSpell() {
        assertEquals(mapper.toSpell(getSpellDTO()).creator,"creator")
    }

    private fun getSpellDTO(): SpellDTO {
        val spell = mockk<SpellDTO>()
        coEvery{spell.name} returns "Kavita"
        coEvery{spell.canBeVerbal} returns "yes"
        coEvery{spell.id} returns "1"
        coEvery{spell.creator} returns "creator"
        coEvery{spell.effect} returns "effect"
        coEvery{spell.incantation} returns "incantation"
        coEvery{spell.light} returns "light"
        coEvery{spell.type} returns "type"
        return spell
    }
}