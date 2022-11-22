package com.example.wizardworld.domain.usecase

import com.example.wizardworld.domain.repository.ProductsRepoInterface

class HouseDetailUseCase (private val productsRepo: ProductsRepoInterface) {
     operator fun invoke(productId:String) = productsRepo.getHouseById(productId)
}