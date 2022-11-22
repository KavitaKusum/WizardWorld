package com.example.wizardworld.domain.usecase

import com.example.wizardworld.domain.repository.ProductsRepoInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class SpellListUseCaseTest : TestCase() {

    private lateinit var useCase:SpellListUseCase
    @MockK
    private lateinit var repo: ProductsRepoInterface

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        useCase= SpellListUseCase(repo)
    }
    fun testInvoke() {
        runBlocking {
            coEvery { repo.getSpellList() } returns flow{}
            useCase.invoke()
            coVerify { repo.getSpellList() }
        }
    }
}