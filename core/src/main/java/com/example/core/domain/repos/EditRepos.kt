package com.example.core.domain.repos

import com.example.core.domain.model.create.CreateOrEditSaleModel

interface EditRepos {
    suspend fun editSale(idSale: String,saleModel: CreateOrEditSaleModel)
}