package com.example.core.domain.use_case.edit

import com.example.core.domain.model.create.CreateOrEditSaleModel
import com.example.core.domain.repos.CreateRepos
import com.example.core.domain.repos.EditRepos

class EditSaleUseCase(
    private val editRepos: EditRepos,
    private val  createRepos: CreateRepos,
) {
    suspend fun execute(
        idSale: String,
        saleModel: CreateOrEditSaleModel,
        listImageUri: List<String>
    ) = runCatching {
        editRepos.editSale(idSale,saleModel)
        createRepos.updateImagesSale(listImageUri,idSale)
    }
}