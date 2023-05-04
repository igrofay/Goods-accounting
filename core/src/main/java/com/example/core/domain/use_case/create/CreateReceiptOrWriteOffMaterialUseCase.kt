package com.example.core.domain.use_case.create

import com.example.core.domain.model.create.CreateReceiptOrWriteOffMaterialModel
import com.example.core.domain.repos.CreateRepos

class CreateReceiptOrWriteOffMaterialUseCase(
    private val createRepos: CreateRepos,
) {
    suspend fun execute(
        isReceipt: Boolean,
        listImageUri: List<String>,
        createReceiptOrWriteOffMaterialModel: CreateReceiptOrWriteOffMaterialModel,
    ) = runCatching {
        val idModel = createRepos.createReceiptOrWriteOffMaterial(isReceipt, createReceiptOrWriteOffMaterialModel)
        createRepos.updateImagesReceiptOrWriteOffMaterial(listImageUri,idModel.id)
    }
}