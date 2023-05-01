package com.example.core.domain.repos

import com.example.core.domain.model.create.CreateProductModel
import com.example.core.domain.model.create.IdModel
import com.example.core.domain.model.product.MaterialModel

interface CreateProductAndMaterialRepos {

    suspend fun createMaterial(materialModel: MaterialModel) : IdModel // id material
    suspend fun createProduct(createProductModel: CreateProductModel) : IdModel // id product

    suspend fun updateImageMaterial(imageUri: String, id: String)
    suspend fun updateImageProduct(imageUri: String, id: String)
}