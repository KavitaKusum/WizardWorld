package com.example.wizardworld.domain.usecase

import com.example.wizardworld.domain.repository.ProductsRepoInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class HouseDetailUseCaseTest : TestCase() {

    private lateinit var useCase:HouseDetailUseCase
    @MockK
    private lateinit var repo: ProductsRepoInterface
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        useCase= HouseDetailUseCase(repo)
    }
    fun testInvoke() {
        runBlocking {
            val id= "1"
            coEvery { repo.getHouseById(id) } returns flow{}
            useCase.invoke(id)
            coVerify { repo.getHouseById(id) }
        }
    }
}