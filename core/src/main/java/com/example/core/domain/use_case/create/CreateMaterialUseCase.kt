package com.example.core.domain.use_case.create

import com.example.core.domain.model.create_or_edit.CreateOrEditMaterialModel
import com.example.core.domain.repos.CreateRepos

class CreateMaterialUseCase(
    private val createRepos: CreateRepos,
) {
    suspend fun execute(createOrEditMaterialModel: CreateOrEditMaterialModel, imageUri: String?) = runCatching<Unit> {
        val idModel = createRepos.createMaterial(createOrEditMaterialModel)
        imageUri?.let { imageUriNotNull->
            createRepos.updateImageMaterial(imageUriNotNull, idModel.id)
        }
    }
}