package com.example.wizardworld.presentation

import com.example.wizardworld.data.elixir
import com.example.wizardworld.data.errorString
import com.example.wizardworld.data.id
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Elixir
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
        viewModel = ElixirDetailsViewModel(elixirDetailUseCase)
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testGetElixirDetailsSuccess() {
        runTest {
            coEvery { elixirDetailUseCase.invoke(id) } returns (flow {
                val elixirObj = mockk<Elixir>()
                coEvery { elixirObj.name } returns elixir
                emit(Result.Success(elixirObj))
            })
            viewModel.getElixirDetails(id)
        }
        assertEquals(elixir, viewModel.viewState.value.data?.name)
    }

    fun testGetElixirDetailsError() {
        runTest {
            coEvery { elixirDetailUseCase.invoke(id) } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getElixirDetails(id)
        }
        assertEquals(errorString, viewModel.viewState.value.error)
    }

    fun testGetElixirDetailsLoading() {
        runTest {
            coEvery { elixirDetailUseCase.invoke(id) } returns (flow { emit(Result.Loading()) })
            viewModel.getElixirDetails(id)
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
}