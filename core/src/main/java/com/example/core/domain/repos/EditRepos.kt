package com.example.core.domain.repos

import com.example.core.domain.model.create_or_edit.CreateOrEditMaterialModel
import com.example.core.domain.model.create_or_edit.CreateOrEditProductModel
import com.example.core.domain.model.create_or_edit.CreateOrEditSaleModel

interface EditRepos {
    suspend fun editSale(idSale: String,saleModel: CreateOrEditSaleModel)
    suspend fun editMaterial(idMaterial: String, materialModel: CreateOrEditMaterialModel)
    suspend fun editProduct(idProduct: String, productModel: CreateOrEditProductModel)
}