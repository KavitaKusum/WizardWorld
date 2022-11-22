package com.example.wizardworld.domain.usecase

import com.example.wizardworld.domain.repository.ProductsRepoInterface

class SpellListUseCase(private val productsRepo: ProductsRepoInterface) {
    operator fun invoke() = productsRepo.getSpellList()
}