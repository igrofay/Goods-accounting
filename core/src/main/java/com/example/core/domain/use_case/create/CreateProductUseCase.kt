package com.example.core.domain.use_case.create

import com.example.core.domain.model.create.CreateProductModel
import com.example.core.domain.repos.CreateRepos

class CreateProductUseCase(
    private val createRepos: CreateRepos
) {
    suspend fun execute(createProductModel: CreateProductModel, imageUri: String?) = runCatching {
        val idModel = createRepos.createProduct(createProductModel)
        imageUri?.let {imageUriNotNull->
            createRepos.updateImageProduct(imageUriNotNull, idModel.id)
        }
    }
}