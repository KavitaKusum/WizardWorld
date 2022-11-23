package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.*
import com.example.wizardworld.data.dto.HeadsDTO
import com.example.wizardworld.data.dto.HouseDTO
import com.example.wizardworld.data.dto.TraitsDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class HouseListAPiResponseMapperTest : TestCase() {
    private lateinit var mapper: HouseListAPiResponseMapper
    public override fun setUp() {
        super.setUp()
        mapper = HouseListAPiResponseMapper()
    }

    fun testToHouseList() {
        assertEquals(mapper.toHouseList(listOf(getHouseDTO()))[0].first, id)
    }

    fun testToHouse() {
        assertEquals(mapper.toHouse(getHouseDTO()).name, house)
    }

    private fun getHouseDTO(): HouseDTO {
        val houseObj = mockk<HouseDTO>()
        coEvery { houseObj.id } returns id
        coEvery { houseObj.name } returns house
        coEvery { houseObj.houseColours } returns ""
        coEvery { houseObj.founder } returns founder
        coEvery { houseObj.animal } returns tiger
        coEvery { houseObj.element } returns ""
        coEvery { houseObj.ghost } returns ""
        coEvery { houseObj.commonRoom } returns ""
        val heads = mockk<HeadsDTO>()
        coEvery { heads.firstName } returns head
        coEvery { heads.lastName } returns ""
        coEvery { houseObj.heads } returns (listOf(heads))
        val traitsObj = mockk<TraitsDTO>()
        coEvery { traitsObj.name } returns trait
        coEvery { houseObj.traits } returns (listOf(traitsObj))
        return houseObj
    }
}
