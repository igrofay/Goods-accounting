package com.example.core.data.repos

import com.example.core.data.data_soure.api.SaleApi
import com.example.core.data.model.create.CreateOrEditOrEditSaleBody.Companion.fromModelToCreateOrEditOrEditSaleBody
import com.example.core.domain.model.create.CreateOrEditSaleModel
import com.example.core.domain.repos.EditRepos

internal class EditReposImpl(
 private val saleApi: SaleApi,
) : EditRepos {
    override suspend fun editSale(idSale: String,saleModel: CreateOrEditSaleModel) {
        saleApi.editSale(idSale, saleModel.fromModelToCreateOrEditOrEditSaleBody())
    }
}