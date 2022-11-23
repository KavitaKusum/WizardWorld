package com.example.wizardworld.presentation

import android.content.Context
import com.example.wizardworld.R
import com.example.wizardworld.data.*
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.model.House
import com.example.wizardworld.domain.model.Spell
import com.example.wizardworld.domain.model.Wizard
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
        coEvery { context.getString(R.string.wizards_list) } returns wizard
        assertEquals(wizard, viewModel.getHeadingText(1, context))
    }

    fun testGetHeadingText2() {
        val context = mockk<Context>()
        coEvery { context.getString(R.string.houses_list) } returns house
        assertEquals(house, viewModel.getHeadingText(2, context))
    }

    fun testGetHeadingText3() {
        val context = mockk<Context>()
        coEvery { context.getString(R.string.elixirs_list) } returns elixir
        assertEquals(elixir, viewModel.getHeadingText(3, context))
    }

    fun testGetHeadingText4() {
        val context = mockk<Context>()
        coEvery { context.getString(R.string.spells_list) } returns spell
        assertEquals(spell, viewModel.getHeadingText(4, context))
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
            coEvery { wizardListUseCase.invoke() } returns (flow {
                val wizardObj = mockk<Wizard>()
                coEvery { wizardObj.name } returns wizard
                coEvery { wizardObj.id } returns id
                coEvery { wizardObj.elixirs } returns (listOf(elixir))
                emit(Result.Success(listOf(wizardObj)))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name, wizard) } returns wizard
            coEvery { context.getString(R.string.one_specialization) } returns one_specialization
            viewModel.getProductList(1, context)
        }
        assertEquals(wizard, viewModel.viewState.value.data?.get(0)?.second)
    }

    fun testGetProductListWizardListSuccessManySpecialization() {
        runTest {
            coEvery { wizardListUseCase.invoke() } returns (flow {
                val wizardObj = mockk<Wizard>()
                coEvery { wizardObj.name } returns wizard
                coEvery { wizardObj.id } returns id
                coEvery { wizardObj.elixirs } returns (listOf(elixir, elixir))
                emit(Result.Success(listOf(wizardObj)))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name, wizard) } returns wizard
            coEvery {
                context.getString(
                    R.string.number_specialization,
                    2
                )
            } returns two_specialization
            viewModel.getProductList(1, context)
        }
        assertEquals(wizard, viewModel.viewState.value.data?.get(0)?.second)
    }

    fun testGetProductListWizardListError() {
        runTest {
            coEvery { wizardListUseCase.invoke() } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getProductList(1, mockk())
        }
        assertEquals(errorString, viewModel.viewState.value.error)
    }

    fun testGetProductListWizardListLoading() {
        runTest {
            coEvery { wizardListUseCase.invoke() } returns (flow { emit(Result.Loading()) })
            viewModel.getProductList(1, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetProductListHouseListSuccess() {
        runTest {
            coEvery { houseListUseCase.invoke() } returns (flow {
                val houseObj = mockk<House>()
                coEvery { houseObj.id } returns id
                coEvery { houseObj.name } returns house
                coEvery { houseObj.houseColours } returns ""
                coEvery { houseObj.founder } returns founder
                coEvery { houseObj.animal } returns tiger
                coEvery { houseObj.element } returns ""
                coEvery { houseObj.ghost } returns ""
                coEvery { houseObj.commonRoom } returns ""
                coEvery { houseObj.heads } returns (listOf())
                coEvery { houseObj.traits } returns (listOf())
                emit(Result.Success(listOf(houseObj)))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name, house) } returns house
            coEvery { context.getString(R.string.founder, founder) } returns founder
            viewModel.getProductList(2, context)
        }
        assertEquals(house, viewModel.viewState.value.data?.get(0)?.second)
    }

    fun testGetProductListHouseListError() {
        runTest {
            coEvery { houseListUseCase.invoke() } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getProductList(2, mockk())
        }
        assertEquals(errorString, viewModel.viewState.value.error)
    }

    fun testGetProductListHouseListLoading() {
        runTest {
            coEvery { houseListUseCase.invoke() } returns (flow { emit(Result.Loading()) })
            viewModel.getProductList(1, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetProductListElixirListSuccess() {
        runTest {
            coEvery { elixirListUseCase.invoke() } returns (flow {
                val elixirObj = mockk<Elixir>()
                coEvery { elixirObj.name } returns elixir
                coEvery { elixirObj.id } returns id
                coEvery { elixirObj.effect } returns effect
                coEvery { elixirObj.sideEffects } returns ""
                coEvery { elixirObj.characteristics } returns characteristics
                coEvery { elixirObj.time } returns ""
                coEvery { elixirObj.difficulty } returns ""
                coEvery { elixirObj.ingredients } returns (listOf())
                coEvery { elixirObj.inventors } returns (listOf())
                coEvery { elixirObj.manufacturer } returns ""
                emit(Result.Success(listOf(elixirObj)))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name, elixir) } returns elixir
            coEvery { context.getString(R.string.effect, effect) } returns effect
            viewModel.getProductList(3, context)
        }
        assertEquals(elixir, viewModel.viewState.value.data?.get(0)?.second)
    }

    fun testGetProductListElixirListError() {
        runTest {
            coEvery { elixirListUseCase.invoke() } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getProductList(3, mockk())
        }
        assertEquals(errorString, viewModel.viewState.value.error)
    }

    fun testGetProductListElixirListLoading() {
        runTest {
            coEvery { elixirListUseCase.invoke() } returns (flow { emit(Result.Loading()) })
            viewModel.getProductList(1, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetProductListSpellListSuccess() {
        runTest {
            coEvery { spellListUseCase.invoke() } returns (flow {
                val spellObj = mockk<Spell>()
                coEvery { spellObj.name } returns spell
                coEvery { spellObj.incantation } returns incantation
                coEvery { spellObj.id } returns id
                coEvery { spellObj.effect } returns effect
                coEvery { spellObj.canBeVerbal } returns ""
                coEvery { spellObj.type } returns ""
                coEvery { spellObj.light } returns ""
                coEvery { spellObj.creator } returns creator
                emit(Result.Success(listOf(spellObj)))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.incantation, incantation) } returns incantation
            coEvery { context.getString(R.string.name, spell) } returns spell
            viewModel.getProductList(4, context)
        }
        assertEquals(spell, viewModel.viewState.value.data?.get(0)?.second)
    }

    fun testGetProductListSpellListError() {
        runTest {
            coEvery { spellListUseCase.invoke() } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getProductList(4, mockk())
        }
        assertEquals(errorString, viewModel.viewState.value.error)
    }

    fun testGetProductListSpellListLoading() {
        runTest {
            coEvery { spellListUseCase.invoke() } returns (flow { emit(Result.Loading()) })
            viewModel.getProductList(1, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
}