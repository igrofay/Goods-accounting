package com.example.core.domain.use_case.create

import com.example.core.domain.model.create.CreateProductModel
import com.example.core.domain.repos.CreateProductAndMaterialRepos

class CreateProductUseCase(
    private val createProductAndMaterialRepos: CreateProductAndMaterialRepos
) {
    suspend fun execute(createProductModel: CreateProductModel, imageUri: String?) = runCatching {
        val id = createProductAndMaterialRepos.createProduct(createProductModel)
        imageUri?.let {imageUriNotNull->
            createProductAndMaterialRepos.updateImageProduct(imageUriNotNull, id)
        }
    }
}