package com.example.wizardworld.presentation

import android.content.Context
import com.example.wizardworld.R
import com.example.wizardworld.domain.Result
import com.example.wizardworld.domain.model.Heads
import com.example.wizardworld.domain.model.House
import com.example.wizardworld.domain.model.Traits
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
        viewModel= HouseDetailsViewModel(houseDetailUseCase)
    }

    public override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    fun testGetHouseDetailsSuccess() {
        runTest {
            coEvery{houseDetailUseCase.invoke("1")} returns(flow {
                val house= mockk<House>()
                coEvery{house.name} returns "House"
                emit(Result.Success(house))
            })
            viewModel.getHouseDetails("1")
        }
        assertEquals("House",viewModel.viewState.value.data?.name)
    }

    fun testGetHouseDetailsError() {
        runTest {
            coEvery{houseDetailUseCase.invoke("1")} returns(flow { emit(Result.Error("Error") )})
            viewModel.getHouseDetails("1")
        }
        assertEquals("Error", viewModel.viewState.value.error)
    }

    fun testGetHouseDetailsLoading() {
        runTest {
            coEvery{houseDetailUseCase.invoke("1")} returns(flow { emit(Result.Loading()) })
            viewModel.getHouseDetails("1")
        }
        assertTrue(viewModel.viewState.value.isLoading)
    }

    fun testGetTraitsList() {
        val context = mockk<Context>()
        coEvery{context.getString(R.string.no_traits)} returns "none"
        val trait= mockk<Traits>()
        coEvery{trait.id} returns "1"
        coEvery{trait.name} returns "Trait"
        assertEquals(viewModel.getTraitsList(listOf(trait))[0],"Trait")
    }

    fun testGetHeadsList() {
        val context = mockk<Context>()
        coEvery{context.getString(R.string.none)} returns "none"
        val head= mockk<Heads>()
        coEvery{head.id} returns "1"
        coEvery{head.name} returns "Kavita Kusum"
        assertEquals(viewModel.getHeadsList(listOf(head))[0],"Kavita Kusum")
    }
}