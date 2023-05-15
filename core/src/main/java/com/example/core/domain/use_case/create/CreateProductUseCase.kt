package com.example.core.domain.use_case.create

import com.example.core.domain.model.create_or_edit.CreateOrEditProductModel
import com.example.core.domain.repos.CreateRepos

class CreateProductUseCase(
    private val createRepos: CreateRepos
) {
    suspend fun execute(createOrEditProductModel: CreateOrEditProductModel, imageUri: String?) = runCatching {
        val idModel = createRepos.createProduct(createOrEditProductModel)
        imageUri?.let {imageUriNotNull->
            createRepos.updateImageProduct(imageUriNotNull, idModel.id)
        }
    }
}