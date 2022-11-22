package com.example.wizardworld.domain.usecase

import com.example.wizardworld.domain.repository.ProductsRepoInterface

class WizardListUseCase(private val productsRemoteRepo: ProductsRepoInterface) {
    operator fun invoke()= productsRemoteRepo.getWizardList()
}