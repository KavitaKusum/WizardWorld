package com.example.wizardworld.presentation

import com.example.wizardworld.data.errorString
import com.example.wizardworld.data.id
import com.example.wizardworld.data.spell
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
        viewModel = SpellDetailsViewModel(useCase)
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testGetSpellDetailsSuccess() {
        runTest {
            coEvery { useCase.invoke(id) } returns (flow {
                val spellObj = mockk<Spell>()
                coEvery { spellObj.name } returns spell
                emit(Result.Success(spellObj))
            })
            viewModel.getSpellDetails(id)
        }
        assertEquals(spell, viewModel.viewState.value.data?.name)
    }

    fun testGetSpellDetailsError() {
        runTest {
            coEvery { useCase.invoke(id) } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getSpellDetails(id)
        }
        assertEquals(errorString, viewModel.viewState.value.error)
    }

    fun testGetSpellDetailsLoading() {
        runTest {
            coEvery { useCase.invoke(id) } returns (flow { emit(Result.Loading()) })
            viewModel.getSpellDetails(id)
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
}