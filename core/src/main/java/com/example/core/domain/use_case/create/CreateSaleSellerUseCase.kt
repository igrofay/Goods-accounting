package com.example.core.domain.use_case.create

import com.example.core.domain.model.create.CreateOrEditSaleModel
import com.example.core.domain.repos.CreateRepos

class CreateSaleSellerUseCase(
    private val createRepos: CreateRepos,
) {
    suspend fun execute(
        createOrEditSaleModel: CreateOrEditSaleModel,
        listImageUri: List<String>,
    ) = runCatching {
        val idModel = createRepos.createSale(createOrEditSaleModel)
        createRepos.updateImagesSale(id = idModel.id, listImageUri = listImageUri)
    }
}