package com.example.wizardworld.presentation

import com.example.wizardworld.data.errorString
import com.example.wizardworld.data.id
import com.example.wizardworld.data.wizard
import com.example.wizardworld.domain.Result
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
        viewModel = WizardDetailsViewModel(useCase)
    }

    public override fun tearDown() {
        super.tearDown()
    }

    fun testGetWizardDetailsSuccess() {
        runTest {
            coEvery { useCase.invoke(id) } returns (flow {
                val wizardObj = mockk<Wizard>()
                coEvery { wizardObj.name } returns wizard
                emit(Result.Success(wizardObj))
            })
            viewModel.getWizardDetails(id)
        }
        assertEquals(wizard, viewModel.viewState.value.data?.name)
    }

    fun testGetWizardDetailsError() {
        runTest {
            coEvery { useCase.invoke(id) } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getWizardDetails(id)
        }
        assertEquals(errorString, viewModel.viewState.value.error)
    }

    fun testGetWizardDetailsLoading() {
        runTest {
            coEvery { useCase.invoke(id) } returns (flow { emit(Result.Loading()) })
            viewModel.getWizardDetails(id)
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
}