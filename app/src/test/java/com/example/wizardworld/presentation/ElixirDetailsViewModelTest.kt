package com.example.wizardworld.presentation

import android.content.Context
import com.example.wizardworld.R
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.model.Ingredient
import com.example.wizardworld.domain.model.Inventor
import com.example.wizardworld.domain.usecase.ElixirDetailUseCase
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
class ElixirDetailsViewModelTest : TestCase() {
    @MockK
    private lateinit var elixirDetailUseCase: ElixirDetailUseCase
    private lateinit var viewModel: ElixirDetailsViewModel

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel= ElixirDetailsViewModel(elixirDetailUseCase)
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testGetElixirDetailsSuccess() {
        runTest {
            val id= "1"
            coEvery{elixirDetailUseCase.invoke(id)} returns(flow {
                val elixir= mockk<Elixir>()
                coEvery{elixir.name} returns "Elixir"
                emit(Result.Success(elixir))
            })
            viewModel.getElixirDetails(id)
        }
        assertEquals("Elixir",viewModel.viewState.value.data?.name)
    }

    fun testGetElixirDetailsError() {
        runTest {
            val id="1"
            coEvery{elixirDetailUseCase.invoke("1")} returns(flow { emit(Result.Error("Error")) })
            viewModel.getElixirDetails(id)
        }
        assertEquals("Error", viewModel.viewState.value.error)
    }

    fun testGetElixirDetailsLoading() {
        runTest {
            coEvery{elixirDetailUseCase.invoke("1")} returns(flow { emit(Result.Loading()) })
            viewModel.getElixirDetails("1")
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetIngredientsList() {
        val context = mockk<Context>()
        coEvery{context.getString(R.string.none)} returns "none"
        val ingredient= mockk<Ingredient>()
        coEvery{ingredient.id} returns "1"
        coEvery{ingredient.name} returns "Ingredient"
        assertEquals(viewModel.getIngredientsList(listOf(ingredient))[0],"Ingredient")
    }

    fun testGetInventorsList() {
        val inventor= mockk<Inventor>()
        coEvery{inventor.name} returns "Kavita"
        coEvery{inventor.id} returns "1"
        assertEquals(viewModel.getInventorsList(listOf(inventor))[0],"Kavita")
    }
}