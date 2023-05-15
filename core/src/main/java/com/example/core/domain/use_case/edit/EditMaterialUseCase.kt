package com.example.core.domain.use_case.edit

import com.example.core.domain.model.create_or_edit.CreateOrEditMaterialModel
import com.example.core.domain.repos.CreateRepos
import com.example.core.domain.repos.EditRepos

class EditMaterialUseCase(
    private val editRepos: EditRepos,
    private val createRepos: CreateRepos,
) {
    suspend fun execute(
        id: String,
        materialModel: CreateOrEditMaterialModel,
        imageUri: String?
    ) = runCatching {
        editRepos.editMaterial(id, materialModel)
        imageUri?.let {
            createRepos.updateImageMaterial(it, id)
        }

    }
}