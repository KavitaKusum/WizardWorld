package com.example.wizardworld.data.mapper

import com.example.wizardworld.data.dto.HeadsDTO
import com.example.wizardworld.data.dto.HouseDTO
import com.example.wizardworld.data.dto.TraitsDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase

class HouseListAPiResponseMapperTest : TestCase() {
    private lateinit var mapper:HouseListAPiResponseMapper
    public override fun setUp() {
        super.setUp()
        mapper= HouseListAPiResponseMapper()
    }

    fun testToHouseList() {
        assertEquals(mapper.toHouseList(listOf(getHouseDTO()))[0].animal,"Tiger")
    }

    fun testToHouse() {
        assertEquals(mapper.toHouse(getHouseDTO()).heads[0].name,"Kavita Kusum")
    }

    private fun getHouseDTO(): HouseDTO {
        val house= mockk<HouseDTO>()
        coEvery{house.animal} returns "Tiger"
        coEvery{house.commonRoom} returns "Common"
        coEvery{house.id} returns "1"
        coEvery{house.element} returns "Element"
        coEvery{house.founder} returns "Kavita"
        coEvery{house.ghost} returns "Ghost"
        coEvery{house.houseColours} returns "Red"
        coEvery{house.name} returns "House"
        val heads = mockk<HeadsDTO>()
        coEvery { heads.id } returns "1"
        coEvery { heads.firstName } returns "Kavita"
        coEvery { heads.lastName } returns "Kusum"
        coEvery { house.heads } returns (listOf(heads))
        val traits = mockk<TraitsDTO>()
        coEvery { traits.id } returns "1"
        coEvery { traits.name } returns "Name"
        coEvery{house.traits} returns(listOf(traits))
        return house
    }
}
