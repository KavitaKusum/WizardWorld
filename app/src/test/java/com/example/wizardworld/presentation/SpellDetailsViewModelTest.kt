package com.example.wizardworld.presentation

import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Spell
import com.example.wizardworld.domain.usecase.SpellDetailsUseCase
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
class SpellDetailsViewModelTest : TestCase() {
    @MockK
    private lateinit var useCase: SpellDetailsUseCase
    private lateinit var viewModel: SpellDetailsViewModel

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel= SpellDetailsViewModel(useCase)
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testGetSpellDetailsSuccess() {
        runTest {
            coEvery{useCase.invoke("1")} returns(flow {
                val spell= mockk<Spell>()
                coEvery{spell.name} returns "Spell"
                emit(Result.Success(spell))
            })
            viewModel.getSpellDetails("1")
        }
        assertEquals("Spell",viewModel.viewState.value.data?.name)
    }

    fun testGetSpellDetailsError() {
        runTest {
            coEvery{useCase.invoke("1")} returns(flow { emit(Result.Error("Error")) })
            viewModel.getSpellDetails("1")
        }
        assertEquals("Error", viewModel.viewState.value.error)
    }

    fun testGetSpellDetailsLoading() {
        runTest {
            coEvery{useCase.invoke("1")} returns(flow { emit(Result.Loading()) })
            viewModel.getSpellDetails("1")
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
}