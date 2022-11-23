package com.example.wizardworld.data.repository

import com.example.wizardworld.data.api.WizardService
import com.example.wizardworld.data.dto.ElixirDTO
import com.example.wizardworld.data.dto.HouseDTO
import com.example.wizardworld.data.dto.SpellDTO
import com.example.wizardworld.data.dto.WizardDTO
import com.example.wizardworld.data.errorString
import com.example.wizardworld.data.mapper.ElixirListAPiResponseMapper
import com.example.wizardworld.data.mapper.HouseListAPiResponseMapper
import com.example.wizardworld.data.mapper.SpellListAPiResponseMapper
import com.example.wizardworld.data.mapper.WizardListAPiResponseMapper
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.model.House
import com.example.wizardworld.domain.model.Spell
import com.example.wizardworld.domain.model.Wizard
import com.example.wizardworld.presentation.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import retrofit2.Response

@ExperimentalCoroutinesApi
class ProductsRepoImplTest : TestCase() {
    private lateinit var repo: ProductsRepoInterface

    @MockK
    private lateinit var wizardService: WizardService

    @MockK
    private lateinit var wizardListAPiResponseMapper: WizardListAPiResponseMapper

    @MockK
    private lateinit var spellListAPiResponseMapper: SpellListAPiResponseMapper

    @MockK
    private lateinit var houseListAPiResponseMapper: HouseListAPiResponseMapper

    @MockK
    private lateinit var elixirListAPiResponseMapper: ElixirListAPiResponseMapper

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
        repo = ProductsRepoImpl(
            wizardService,
            wizardListAPiResponseMapper,
            spellListAPiResponseMapper,
            elixirListAPiResponseMapper,
            houseListAPiResponseMapper
        )
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testGetWizardListSuccess() {
        runTest {
            val response = mockk<Response<List<WizardDTO>>>()
            coEvery { response.isSuccessful } returns true
            val list = listOf(mockk<WizardDTO>())
            coEvery { response.body() } returns list
            coEvery { wizardListAPiResponseMapper.toWizardList(list) } returns listOf(mockk())
            coEvery { wizardService.getWizards() } returns response
            assertEquals(1, repo.getWizardList().first().result?.size)
        }
    }

    fun testGetWizardListError() {
        runTest {
            val response = mockk<Response<List<WizardDTO>>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns errorString
            coEvery { wizardService.getWizards() } returns response
            assertEquals(errorString, repo.getWizardList().first().msg)
        }
    }


    fun testGetWizardByIdSuccess() {
        runTest {
            val id = "1"
            val wizardDTO = mockk<WizardDTO>()
            val wizard = mockk<Wizard>()
            val response = mockk<Response<WizardDTO>>()
            coEvery { response.isSuccessful } returns true
            coEvery { response.body() } returns wizardDTO
            coEvery { wizardListAPiResponseMapper.toWizard(wizardDTO) } returns wizard
            coEvery { wizardService.getWizardById(id) } returns response
            assertEquals(wizard, repo.getWizardById(id).first().result)
        }
    }

    fun testGetWizardByIdError() {
        runTest {
            val id = "1"
            val response = mockk<Response<WizardDTO>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns errorString
            coEvery { wizardService.getWizardById(id) } returns response
            assertEquals(errorString, repo.getWizardById(id).first().msg)
        }
    }

    fun testGetSpellListSuccess() {
        runTest {
            val response = mockk<Response<List<SpellDTO>>>()
            coEvery { response.isSuccessful } returns true
            val list = listOf(mockk<SpellDTO>())
            coEvery { response.body() } returns list
            coEvery { spellListAPiResponseMapper.toSpellList(list) } returns listOf(mockk())
            coEvery { wizardService.getSpells() } returns response
            assertEquals(1, repo.getSpellList().first().result?.size)
        }
    }

    fun testGetSpellListError() {
        runTest {
            val response = mockk<Response<List<SpellDTO>>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns errorString
            coEvery { wizardService.getSpells() } returns response
            assertEquals(errorString, repo.getSpellList().first().msg)
        }
    }

    fun testGetSpellByIdSuccess() {
        runTest {
            val id = "1"
            val spellDTO = mockk<SpellDTO>()
            val spell = mockk<Spell>()
            val response = mockk<Response<SpellDTO>>()
            coEvery { response.isSuccessful } returns true
            coEvery { response.body() } returns spellDTO
            coEvery { spellListAPiResponseMapper.toSpell(spellDTO) } returns spell
            coEvery { wizardService.getSpellById(id) } returns response
            assertEquals(spell, repo.getSpellById(id).first().result)
        }
    }

    fun testGetSpellByIdError() {
        runTest {
            val id = "1"
            val response = mockk<Response<SpellDTO>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns errorString
            coEvery { wizardService.getSpellById(id) } returns response
            assertEquals(errorString, repo.getSpellById(id).first().msg)
        }
    }

    fun testGetElixirListSuccess() {
        runTest {
            val response = mockk<Response<List<ElixirDTO>>>()
            coEvery { response.isSuccessful } returns true
            val list = listOf(mockk<ElixirDTO>())
            coEvery { response.body() } returns list
            coEvery { elixirListAPiResponseMapper.toElixirList(list) } returns listOf(mockk())
            coEvery { wizardService.getElixirs() } returns response
            assertEquals(1, repo.getElixirList().first().result?.size)
        }
    }

    fun testGetElixirListError() {
        runTest {
            val response = mockk<Response<List<ElixirDTO>>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns errorString
            coEvery { wizardService.getElixirs() } returns response
            assertEquals(errorString, repo.getElixirList().first().msg)
        }
    }

    fun testGetElixirByIdSuccess() {
        runTest {
            val id = "1"
            val elixirDTO = mockk<ElixirDTO>()
            val elixir = mockk<Elixir>()
            val response = mockk<Response<ElixirDTO>>()
            coEvery { response.isSuccessful } returns true
            coEvery { response.body() } returns elixirDTO
            coEvery { elixirListAPiResponseMapper.toElixir(elixirDTO) } returns elixir
            coEvery { wizardService.getElixirById(id) } returns response
            assertEquals(elixir, repo.getElixirById(id).first().result)
        }
    }

    fun testGetElixirByIdError() {
        runTest {
            val id = "1"
            val response = mockk<Response<ElixirDTO>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns errorString
            coEvery { wizardService.getElixirById(id) } returns response
            assertEquals(errorString, repo.getElixirById(id).first().msg)
        }
    }

    fun testGetHouseListSuccess() {
        runTest {
            val response = mockk<Response<List<HouseDTO>>>()
            coEvery { response.isSuccessful } returns true
            val list = listOf(mockk<HouseDTO>())
            coEvery { response.body() } returns list
            coEvery { houseListAPiResponseMapper.toHouseList(list) } returns listOf(mockk())
            coEvery { wizardService.getHouses() } returns response
            assertEquals(1, repo.getHouseList().first().result?.size)
        }
    }

    fun testGetHouseListError() {
        runTest {
            val response = mockk<Response<List<HouseDTO>>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns errorString
            coEvery { wizardService.getHouses() } returns response
            assertEquals(errorString, repo.getHouseList().first().msg)
        }
    }

    fun testGetHouseByIdSuccess() {
        runTest {
            val id = "1"
            val houseDTO = mockk<HouseDTO>()
            val house = mockk<House>()
            val response = mockk<Response<HouseDTO>>()
            coEvery { response.isSuccessful } returns true
            coEvery { response.body() } returns houseDTO
            coEvery { houseListAPiResponseMapper.toHouse(houseDTO) } returns house
            coEvery { wizardService.getHouseById(id) } returns response
            assertEquals(house, repo.getHouseById(id).first().result)
        }
    }

    fun testGetHouseByIdError() {
        runTest {
            val id = "1"
            val response = mockk<Response<HouseDTO>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns errorString
            coEvery { wizardService.getHouseById(id) } returns response
            assertEquals(errorString, repo.getHouseById(id).first().msg)
        }
    }
}