package com.example.wizardworld.presentation

import android.content.Context
import com.example.wizardworld.R
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.*
import com.example.wizardworld.domain.usecase.ElixirListUseCase
import com.example.wizardworld.domain.usecase.HouseListUseCase
import com.example.wizardworld.domain.usecase.SpellListUseCase
import com.example.wizardworld.domain.usecase.WizardListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule

@ExperimentalCoroutinesApi
class ProductListViewModelTest : TestCase() {
    @MockK
    private lateinit var wizardListUseCase: WizardListUseCase

    @MockK
    private lateinit var spellListUseCase: SpellListUseCase

    @MockK
    private lateinit var elixirListUseCase: ElixirListUseCase

    @MockK
    private lateinit var houseListUseCase: HouseListUseCase
    private lateinit var viewModel: ProductListViewModel

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = ProductListViewModel(
            wizardListUseCase,
            spellListUseCase,
            elixirListUseCase,
            houseListUseCase
        )
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testGetHeadingText1() {
        val context = mockk<Context>()
        coEvery{context.getString(R.string.wizards_list)} returns "Wizard"
        assertEquals("Wizard", viewModel.getHeadingText(1, context))
    }

    fun testGetHeadingText2() {
        val context = mockk<Context>()
        coEvery{context.getString(R.string.houses_list)} returns "House"
        assertEquals("House", viewModel.getHeadingText(2, context))
    }

    fun testGetHeadingText3() {
        val context = mockk<Context>()
        coEvery{context.getString(R.string.elixirs_list)} returns "Elixir"
        assertEquals("Elixir", viewModel.getHeadingText(3, context))
    }

    fun testGetHeadingText4() {
        val context = mockk<Context>()
        coEvery{context.getString(R.string.spells_list)} returns "Spell"
        assertEquals("Spell", viewModel.getHeadingText(4, context))
    }

    fun testGetHeadingTextNone() {
        assertEquals("", viewModel.getHeadingText(5, mockk()))
    }

    fun testGetProductListInvalidChoice() {
        runBlocking {
            viewModel.getProductList(5, mockk())
            assertNotNull(viewModel.viewState.value)
        }
    }

    fun testGetProductListWizardListSuccess() {
        runTest {
            val name= "Kavita"
            coEvery{wizardListUseCase.invoke()} returns(flow {
                val wizard= mockk<Wizard>()
                coEvery{wizard.name} returns name
                coEvery{wizard.id} returns "1"
                val elixir = mockk<Elixir>()
                coEvery{elixir.characteristics} returns "characteristics"
                coEvery{elixir.difficulty} returns "Hard"
                coEvery{elixir.id} returns "1"
                coEvery{elixir.effect} returns "effect"
                coEvery{elixir.sideEffects} returns "sideEffects"
                coEvery{elixir.manufacturer} returns "manufacturer"
                coEvery{elixir.time} returns "time"
                coEvery{elixir.name} returns "elixir"
                val inventor=mockk<Inventor>()
                coEvery { inventor.id } returns "1"
                coEvery{inventor.name} returns "Kavita"
                coEvery{elixir.inventors} returns(listOf(inventor))
                val ingredient=mockk<Ingredient>()
                coEvery{ingredient.id} returns "1"
                coEvery{ingredient.name} returns "Ingredient"
                coEvery{elixir.ingredients} returns(listOf(ingredient))
                coEvery{wizard.elixirs} returns(listOf(elixir))
                emit(Result.Success(listOf(wizard)))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name,name) } returns "Kavita"
            coEvery { context.getString(R.string.one_specialization) } returns "1 specializatopn"
            viewModel.getProductList(1, context)
        }
        assertEquals("Kavita", viewModel.viewState.value.data!![0].second)
    }

    fun testGetProductListWizardListSuccessManySpecialization () {
        runTest {
            val name = "Kavita"
            coEvery{wizardListUseCase.invoke()} returns(flow {
                val wizard = mockk<Wizard>()
                coEvery{wizard.name} returns name
                coEvery{wizard.id} returns "1"
                coEvery{wizard.elixirs} returns(listOf(mockk(),mockk()))
                emit(Result.Success(listOf(wizard)))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name,name) } returns "Kavita"
            coEvery { context.getString(R.string.number_specialization, 2) } returns "2 specializatopn"
            viewModel.getProductList(1, context)
        }
        assertEquals("Kavita", viewModel.viewState.value.data!![0].second)
    }

    fun testGetProductListWizardListError() {
        runTest {
            coEvery{wizardListUseCase.invoke()} returns(flow { emit(Result.Error("Error"))})
            viewModel.getProductList(1, mockk())
        }
        assertEquals("Error", viewModel.viewState.value.error)
    }

    fun testGetProductListWizardListLoading() {
        runTest {
            coEvery{wizardListUseCase.invoke()} returns(flow { emit(Result.Loading()) })
            viewModel.getProductList(1, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetProductListHouseListSuccess() {
        runTest {
            val houseName= "House"
            val founder = "Founder"
            coEvery{houseListUseCase.invoke()} returns(flow {
                val house = mockk<House>()
                coEvery{house.animal} returns "Tiger"
                coEvery{house.commonRoom} returns "Common"
                coEvery{house.id} returns "1"
                coEvery{house.element} returns "Element"
                coEvery{house.founder} returns founder
                coEvery{house.ghost} returns "Ghost"
                coEvery{house.houseColours} returns "Red"
                coEvery{house.name} returns houseName
                val heads = mockk<Heads>()
                coEvery { heads.id } returns "1"
                coEvery { heads.name } returns "Kavita Kusum"
                coEvery { house.heads } returns (listOf(heads))
                val traits = mockk<Traits>()
                coEvery { traits.id } returns "1"
                coEvery { traits.name } returns "Name"
                coEvery{house.traits} returns(listOf(traits))
                emit(Result.Success(listOf(house)))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name,houseName) } returns "House"
            coEvery { context.getString(R.string.founder, founder) } returns "Founder"
            viewModel.getProductList(2,context )
        }
        assertEquals("House", viewModel.viewState.value.data!![0].second)
    }

    fun testGetProductListHouseListError() {
        runTest {
            coEvery{houseListUseCase.invoke()} returns(flow { emit(Result.Error("Error") )})
            viewModel.getProductList(2, mockk())
        }
        assertEquals("Error", viewModel.viewState.value.error)
    }

    fun testGetProductListHouseListLoading() {
        runTest {
            coEvery{houseListUseCase.invoke()} returns(flow { emit(Result.Loading()) })
            viewModel.getProductList(1, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetProductListElixirListSuccess() {
        runTest {
            val name= "elixir"
            val effect = "effect"
            coEvery{elixirListUseCase.invoke()} returns(flow {
                val elixir = mockk<Elixir>()
                coEvery{elixir.characteristics} returns "characteristics"
                coEvery{elixir.difficulty} returns "Hard"
                coEvery{elixir.id} returns "1"
                coEvery{elixir.effect} returns effect
                coEvery{elixir.sideEffects} returns "sideEffects"
                coEvery{elixir.manufacturer} returns "manufacturer"
                coEvery{elixir.time} returns "time"
                coEvery{elixir.name} returns name
                coEvery{elixir.ingredients} returns(listOf())
                coEvery{elixir.inventors} returns(listOf())
                emit(Result.Success(listOf(elixir)))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name, name) } returns "elixir"
            coEvery { context.getString(R.string.effect, effect) } returns "effect"
            viewModel.getProductList(3, context)
        }
        assertEquals("elixir", viewModel.viewState.value.data!![0].second)
    }

    fun testGetProductListElixirListError() {
        runTest {
            coEvery{elixirListUseCase.invoke()} returns(flow { emit(Result.Error("Error") )})
            viewModel.getProductList(3, mockk())
        }
        assertEquals("Error", viewModel.viewState.value.error)
    }

    fun testGetProductListElixirListLoading() {
        runTest {
            coEvery{elixirListUseCase.invoke()} returns(flow { emit(Result.Loading()) })
            viewModel.getProductList(1, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetProductListSpellListSuccess() {
        runTest {
            val incantation= "incantation"
            val name = "Kavita"
            coEvery{spellListUseCase.invoke()} returns(flow {
                val spell = mockk<Spell>()
                coEvery{spell.name} returns name
                coEvery{spell.canBeVerbal} returns "yes"
                coEvery{spell.id} returns "1"
                coEvery{spell.creator} returns "creator"
                coEvery{spell.effect} returns "effect"
                coEvery{spell.incantation} returns incantation
                coEvery{spell.light} returns "light"
                coEvery{spell.type} returns "type"
                emit(Result.Success(listOf(spell)))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.incantation, incantation) } returns "incantation"
            coEvery { context.getString(R.string.name, name) } returns "Kavita"
            viewModel.getProductList(4, context)
        }
        assertEquals("Kavita", viewModel.viewState.value.data!![0].second)
    }

    fun testGetProductListSpellListError() {
        runTest {
            coEvery{spellListUseCase.invoke()} returns(flow { emit(Result.Error("Error") )})
            viewModel.getProductList(4, mockk())
        }
        assertEquals("Error", viewModel.viewState.value.error)
    }

    fun testGetProductListSpellListLoading() {
        runTest {
            coEvery{spellListUseCase.invoke()} returns(flow { emit(Result.Loading()) })
            viewModel.getProductList(1, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
}