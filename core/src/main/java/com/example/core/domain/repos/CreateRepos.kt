package com.example.core.domain.repos

import com.example.core.domain.model.create.CreateMaterialModel
import com.example.core.domain.model.create.CreateProductModel
import com.example.core.domain.model.create.CreateReceiptOrWriteOffMaterialModel
import com.example.core.domain.model.create.CreateOrEditSaleModel
import com.example.core.domain.model.create.IdModel

interface CreateRepos {

    suspend fun createMaterial(createMaterialModel: CreateMaterialModel) : IdModel // id material
    suspend fun createProduct(createProductModel: CreateProductModel) : IdModel // id product

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