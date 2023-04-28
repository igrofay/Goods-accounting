package com.example.core.domain.repos

import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.model.product.ProductModel

interface ProductsAndMaterialsRepos {
    suspend fun getMaterials() : List<MaterialModel>
    suspend fun getProducts() : List<ProductModel>
}