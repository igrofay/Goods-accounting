package com.example.core.domain.use_case.edit

import com.example.core.domain.model.create_or_edit.CreateOrEditProductModel
import com.example.core.domain.repos.CreateRepos
import com.example.core.domain.repos.EditRepos

class EditProductUseCase(
    private val editRepos: EditRepos,
    private val createRepos: CreateRepos,
) {
    suspend fun execute(
        idProduct: String,
        createOrEditProductModel: CreateOrEditProductModel,
        imageUri: String?
    ) = runCatching {
        editRepos.editProduct(idProduct, createOrEditProductModel)
        imageUri?.let {
            createRepos.updateImageProduct(it, idProduct)
        }
    }
}