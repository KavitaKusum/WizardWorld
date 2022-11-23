package com.example.wizardworld.domain.usecase

import com.example.wizardworld.data.id
import com.example.wizardworld.data.repository.ProductsRepoInterface
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class WizardDetailUseCaseTest : TestCase() {

    private lateinit var useCase: WizardDetailUseCase

    @MockK
    private lateinit var repo: ProductsRepoInterface
    public override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
        useCase = WizardDetailUseCase(repo)
    }

    fun testInvoke() {
        runBlocking {
            coEvery { repo.getWizardById(id) } returns flow {}
            useCase.invoke(id)
            coVerify { repo.getWizardById(id) }
        }
    }
}