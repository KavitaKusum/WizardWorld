package com.example.wizardworld.domain.usecase

import com.example.wizardworld.domain.repository.ProductsRepoInterface

class WizardDetailUseCase(private val productsRepo: ProductsRepoInterface) {
     operator fun invoke(productId:String) = productsRepo.getWizardById(productId)
}