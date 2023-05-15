package com.example.core.domain.repos

import com.example.core.domain.model.create_or_edit.CreateOrEditMaterialModel
import com.example.core.domain.model.create_or_edit.CreateOrEditProductModel
import com.example.core.domain.model.create_or_edit.CreateReceiptOrWriteOffMaterialModel
import com.example.core.domain.model.create_or_edit.CreateOrEditSaleModel
import com.example.core.domain.model.create_or_edit.IdModel

interface CreateRepos {

    suspend fun createMaterial(createOrEditMaterialModel: CreateOrEditMaterialModel) : IdModel // id material
    suspend fun createProduct(createOrEditProductModel: CreateOrEditProductModel) : IdModel // id product

    suspend fun updateImageMaterial(imageUri: String, id: String)
    suspend fun updateImageProduct(imageUri: String, id: String)

    suspend fun createReceiptOrWriteOffMaterial(
        isReceipt:Boolean,
        createReceiptOrWriteOffMaterialModel: CreateReceiptOrWriteOffMaterialModel
    ) : IdModel
    suspend fun updateImagesReceiptOrWriteOffMaterial(
        listImageUri: List<String>,
        id: String
    )
    suspend fun createSale(createOrEditSaleModel: CreateOrEditSaleModel) : IdModel
    suspend fun updateImagesSale(listImageUri: List<String>, id: String)
}