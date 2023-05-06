package com.example.core.domain.use_case.product

import com.example.core.domain.repos.ProductsAndMaterialsRepos

class GetMapIdToMaterialModelUseCase(
    private val productsAndMaterialsRepos: ProductsAndMaterialsRepos,
) {
    suspend fun execute() = runCatching {
        productsAndMaterialsRepos.getMaterials().associateBy { it.id }
    }
}