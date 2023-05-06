package com.example.core.domain.repos

import com.example.core.domain.model.create.CreateMaterialModel
import com.example.core.domain.model.create.CreateProductModel
import com.example.core.domain.model.create.CreateReceiptOrWriteOffMaterialModel
import com.example.core.domain.model.create.CreateSaleModel
import com.example.core.domain.model.create.IdModel
import com.example.core.domain.model.product.MaterialModel

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
    suspend fun createSale(createSaleModel: CreateSaleModel) : IdModel
    suspend fun updateImagesSale(listImageUri: List<String>, id: String)
}