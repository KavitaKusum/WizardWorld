package com.example.wizardworld.presentation

import com.example.wizardworld.data.errorString
import com.example.wizardworld.data.house
import com.example.wizardworld.data.id
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.House
import com.example.wizardworld.domain.usecase.HouseDetailUseCase
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
class HouseDetailsViewModelTest : TestCase() {

    @MockK
    private lateinit var houseDetailUseCase: HouseDetailUseCase
    private lateinit var viewModel: HouseDetailsViewModel

    @get:Rule
    val mainCoroutineRule = CoroutineTestRule()

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = HouseDetailsViewModel(houseDetailUseCase)
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testGetHouseDetailsSuccess() {
        runTest {
            coEvery { houseDetailUseCase.invoke(id) } returns (flow {
                val houseObj = mockk<House>()
                coEvery { houseObj.name } returns house
                emit(Result.Success(houseObj))
            })
            viewModel.getHouseDetails(id)
        }
        assertEquals(house, viewModel.viewState.value.data?.name)
    }

    fun testGetHouseDetailsError() {
        runTest {
            coEvery { houseDetailUseCase.invoke(id) } returns (flow { emit(Result.Error(errorString)) })
            viewModel.getHouseDetails(id)
        }
        assertEquals(errorString, viewModel.viewState.value.error)
    }

    fun testGetHouseDetailsLoading() {
        runTest {
            coEvery { houseDetailUseCase.invoke(id) } returns (flow { emit(Result.Loading()) })
            viewModel.getHouseDetails(id)
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }
}