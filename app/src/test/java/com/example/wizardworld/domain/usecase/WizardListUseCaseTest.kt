package com.example.wizardworld.domain.usecase

import com.example.wizardworld.domain.repository.ProductsRepoInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class WizardListUseCaseTest : TestCase() {

    private lateinit var useCase:WizardListUseCase
    @MockK
    private lateinit var repo: ProductsRepoInterface

    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        useCase= WizardListUseCase(repo)
    }
    fun testInvoke() {
        runBlocking {
            coEvery { repo.getWizardList() } returns flow{}
            useCase.invoke()
            coVerify { repo.getWizardList() }
        }
    }
}