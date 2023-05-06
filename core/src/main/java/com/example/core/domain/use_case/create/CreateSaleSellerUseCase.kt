package com.example.core.domain.use_case.create

import com.example.core.domain.model.create.CreateSaleModel
import com.example.core.domain.model.sale.SaleModel
import com.example.core.domain.repos.CreateRepos

class CreateSaleSellerUseCase(
    private val createRepos: CreateRepos,
) {
    suspend fun execute(
        createSaleModel: CreateSaleModel,
        listImageUri: List<String>,
    ) = runCatching {

    }
}