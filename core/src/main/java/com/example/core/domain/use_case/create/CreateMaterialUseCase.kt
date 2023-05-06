package com.example.core.domain.use_case.create

import com.example.core.domain.model.create.CreateMaterialModel
import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.repos.CreateRepos

class CreateMaterialUseCase(
    private val createRepos: CreateRepos,
) {
    suspend fun execute(createMaterialModel: CreateMaterialModel, imageUri: String?) = runCatching<Unit> {
        val idModel = createRepos.createMaterial(createMaterialModel)
        imageUri?.let { imageUriNotNull->
            createRepos.updateImageMaterial(imageUriNotNull, idModel.id)
        }
    }
}