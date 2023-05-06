package com.example.core.domain.use_case.product

import com.example.core.domain.repos.ProductsAndMaterialsRepos

class GetMapIdToProductModelUseCase(
    private val productsAndMaterialsRepos: ProductsAndMaterialsRepos
) {
    suspend fun execute() = runCatching {
        productsAndMaterialsRepos.getProducts().associateBy { it.id }
    }
}