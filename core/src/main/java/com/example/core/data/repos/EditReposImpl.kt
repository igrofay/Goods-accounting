package com.example.core.data.repos

import com.example.core.data.data_soure.api.MaterialApi
import com.example.core.data.data_soure.api.ProductApi
import com.example.core.data.data_soure.api.SaleApi
import com.example.core.data.model.create_or_edit.CreateOrEditMaterialBody.Companion.fromModelToCreateMaterialBody
import com.example.core.data.model.create_or_edit.CreateOrEditOrEditSaleBody.Companion.fromModelToCreateOrEditOrEditSaleBody
import com.example.core.data.model.create_or_edit.CreateOrEditProductBody.Companion.fromModelToCreateOrEditProductBody
import com.example.core.domain.model.create_or_edit.CreateOrEditMaterialModel
import com.example.core.domain.model.create_or_edit.CreateOrEditProductModel
import com.example.core.domain.model.create_or_edit.CreateOrEditSaleModel
import com.example.core.domain.repos.EditRepos

internal class EditReposImpl(
 private val saleApi: SaleApi,
 private val materialApi: MaterialApi,
 private val productApi: ProductApi,
) : EditRepos {
    override suspend fun editSale(idSale: String,saleModel: CreateOrEditSaleModel) {
        saleApi.editSale(idSale, saleModel.fromModelToCreateOrEditOrEditSaleBody())
    }

    override suspend fun editMaterial(idMaterial: String, materialModel: CreateOrEditMaterialModel) {
        materialApi.editMaterial(idMaterial, materialModel.fromModelToCreateMaterialBody())
    }

    override suspend fun editProduct(idProduct: String, productModel: CreateOrEditProductModel) {
        productApi.editProduct(idProduct, productModel.fromModelToCreateOrEditProductBody())
    }
}