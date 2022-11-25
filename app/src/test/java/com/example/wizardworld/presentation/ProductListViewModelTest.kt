package com.example.wizardworld.presentation

import android.content.Context
import com.example.wizardworld.CHOICE
import com.example.wizardworld.R
import com.example.wizardworld.data.*
import com.example.wizardworld.domain.Result
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
        assertEquals(wizard, viewModel.getHeadingText(CHOICE.ONE.value, context))
    }

    fun testGetHeadingText2() {
        val context = mockk<Context>()
        coEvery { context.getString(R.string.houses_list) } returns house
        assertEquals(house, viewModel.getHeadingText(CHOICE.TWO.value, context))
    }

    fun testGetHeadingText3() {
        val context = mockk<Context>()
        coEvery { context.getString(R.string.elixirs_list) } returns elixir
        assertEquals(elixir, viewModel.getHeadingText(CHOICE.THREE.value, context))
    }

    fun testGetHeadingText4() {
        val context = mockk<Context>()
        coEvery { context.getString(R.string.spells_list) } returns spell
        assertEquals(spell, viewModel.getHeadingText(CHOICE.FOUR.value, context))
    }

    fun testGetProductListWizardListSuccess() {
        runTest {
            coEvery { wizardListUseCase.invoke() } returns (flow {
                emit(Result.Success(listOf(Triple(id, wizard, id))))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name, wizard) } returns wizard
            coEvery { context.getString(R.string.one_specialization) } returns one_specialization
            viewModel.getProductList(CHOICE.ONE.value, context)
        }
        assertEquals(wizard, viewModel.viewState.value.result[0].second)
    }

    fun testGetProductListWizardListSuccessManySpecialization() {
        runTest {
            coEvery { wizardListUseCase.invoke() } returns (flow {
                emit(Result.Success(listOf(Triple(id, wizard, two))))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name, wizard) } returns wizard
            coEvery {
                context.getString(
                    R.string.number_specialization,
                    two
                )
            } returns two_specialization
            viewModel.getProductList(CHOICE.ONE.value, context)
        }
        assertEquals(wizard, viewModel.viewState.value.result[0].second)
    }

    fun testGetProductListWizardListError() {
        runTest {
            coEvery { wizardListUseCase.invoke() } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getProductList(CHOICE.ONE.value, mockk())
        }
        assertEquals(errorString, viewModel.viewState.value.msg)
    }

    fun testGetProductListWizardListLoading() {
        runTest {
            coEvery { wizardListUseCase.invoke() } returns (flow { emit(Result.Loading()) })
            viewModel.getProductList(CHOICE.ONE.value, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetProductListHouseListSuccess() {
        runTest {
            coEvery { houseListUseCase.invoke() } returns (flow {
                emit(Result.Success(listOf(Triple(id, house, founder))))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name, house) } returns house
            coEvery { context.getString(R.string.founder, founder) } returns founder
            viewModel.getProductList(CHOICE.TWO.value, context)
        }
        assertEquals(house, viewModel.viewState.value.result[0].second)
    }

    fun testGetProductListHouseListError() {
        runTest {
            coEvery { houseListUseCase.invoke() } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getProductList(CHOICE.TWO.value, mockk())
        }
        assertEquals(errorString, viewModel.viewState.value.msg)
    }

    fun testGetProductListHouseListLoading() {
        runTest {
            coEvery { houseListUseCase.invoke() } returns (flow { emit(Result.Loading()) })
            viewModel.getProductList(CHOICE.TWO.value, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetProductListElixirListSuccess() {
        runTest {
            coEvery { elixirListUseCase.invoke() } returns (flow {
                emit(Result.Success(listOf(Triple(id, elixir, effect))))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.name, elixir) } returns elixir
            coEvery { context.getString(R.string.effect, effect) } returns effect
            viewModel.getProductList(CHOICE.THREE.value, context)
        }
        assertEquals(elixir, viewModel.viewState.value.result[0].second)
    }

    fun testGetProductListElixirListError() {
        runTest {
            coEvery { elixirListUseCase.invoke() } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getProductList(CHOICE.THREE.value, mockk())
        }
        assertEquals(errorString, viewModel.viewState.value.msg)
    }

    fun testGetProductListElixirListLoading() {
        runTest {
            coEvery { elixirListUseCase.invoke() } returns (flow { emit(Result.Loading()) })
            viewModel.getProductList(CHOICE.THREE.value, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetProductListSpellListSuccess() {
        runTest {
            coEvery { spellListUseCase.invoke() } returns (flow {
                emit(Result.Success(listOf(Triple(id, spell, incantation))))
            })
            val context = mockk<Context>()
            coEvery { context.getString(R.string.incantation, incantation) } returns incantation
            coEvery { context.getString(R.string.name, spell) } returns spell
            viewModel.getProductList(CHOICE.FOUR.value, context)
        }
        assertEquals(spell, viewModel.viewState.value.result[0].second)
    }

    fun testGetProductListSpellListError() {
        runTest {
            coEvery { spellListUseCase.invoke() } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getProductList(CHOICE.FOUR.value, mockk())
        }
        assertEquals(errorString, viewModel.viewState.value.msg)
    }

    fun testGetProductListSpellListLoading() {
        runTest {
            coEvery { spellListUseCase.invoke() } returns (flow { emit(Result.Loading()) })
            viewModel.getProductList(CHOICE.FOUR.value, mockk())
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
}