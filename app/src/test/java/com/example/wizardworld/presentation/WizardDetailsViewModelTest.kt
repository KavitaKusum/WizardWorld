package com.example.wizardworld.presentation

import android.content.Context
import com.example.wizardworld.R
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Elixir
import com.example.wizardworld.domain.model.Wizard
import com.example.wizardworld.domain.usecase.WizardDetailUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule

@ExperimentalCoroutinesApi
class WizardDetailsViewModelTest : TestCase() {
    @MockK
    private lateinit var useCase: WizardDetailUseCase
    private lateinit var viewModel: WizardDetailsViewModel

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel= WizardDetailsViewModel(useCase)
    }

    public override fun tearDown() {
        super.tearDown()
    }

    fun testGetWizardDetailsSuccess() {
        runTest {
            coEvery{useCase.invoke("1")} returns(flow {
                val wizard= mockk<Wizard>()
                coEvery{wizard.name} returns "Wizard"
                emit(Result.Success(wizard))
            })
            viewModel.getWizardDetails("1")
        }
        assertEquals("Wizard",viewModel.viewState.value.data?.name)
    }

    fun testGetWizardDetailsError() {
        runTest {
            coEvery{useCase.invoke("1")} returns(flow { emit(Result.Error("Error")) })
            viewModel.getWizardDetails("1")
        }
        assertEquals("Error", viewModel.viewState.value.error)
    }

    fun testGetWizardDetailsLoading() {
        runTest {
            coEvery{useCase.invoke("1")} returns(flow { emit(Result.Loading()) })
            viewModel.getWizardDetails("1")
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
    
    fun testGetElixirList() {
        val context=mockk<Context>()
        coEvery{context.getString(R.string.no_elixir)} returns "None"
        val elixir= mockk<Elixir>()
        coEvery{elixir.id} returns "1"
        coEvery{elixir.name} returns "Elixir"
        assertEquals(viewModel.getElixirList( listOf(elixir))[0],"Elixir")
    }
}