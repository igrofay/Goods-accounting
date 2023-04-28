package com.example.core.domain.use_case.create

import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.repos.CreateProductAndMaterialRepos

class CreateMaterialUseCase(
    private val createProductAndMaterialRepos: CreateProductAndMaterialRepos,
) {
    suspend fun execute(materialModel: MaterialModel) = runCatching<Unit> {
        val id = createProductAndMaterialRepos.createMaterial(materialModel)
        materialModel.imageUrl?.let { imageUriNotNull->
            createProductAndMaterialRepos.updateImageMaterial(imageUriNotNull, id)
        }
    }
}