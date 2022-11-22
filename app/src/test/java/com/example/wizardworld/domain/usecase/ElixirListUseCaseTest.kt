package com.example.wizardworld.domain.usecase

import com.example.wizardworld.domain.repository.ProductsRepoInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class ElixirListUseCaseTest : TestCase() {
    private lateinit var useCase:ElixirListUseCase
    @MockK
    private lateinit var repo: ProductsRepoInterface

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        useCase= ElixirListUseCase(repo)
    }
    fun testInvoke() {
        runBlocking {
            coEvery { repo.getElixirList() } returns flow{}
            useCase.invoke()
            coVerify { repo.getElixirList() }
        }
    }
}