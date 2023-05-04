package com.example.core.domain.use_case.create

import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.repos.CreateRepos

class CreateMaterialUseCase(
    private val createRepos: CreateRepos,
) {
    suspend fun execute(materialModel: MaterialModel) = runCatching<Unit> {
//        return@runCatching Unit
        val idModel = createRepos.createMaterial(materialModel)
        materialModel.imageUrl?.let { imageUriNotNull->
            createRepos.updateImageMaterial(imageUriNotNull, idModel.id)
        }
    }
}