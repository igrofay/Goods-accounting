package com.example.core.data.repos

import com.example.core.data.data_soure.api.MaterialApi
import com.example.core.data.data_soure.api.ProductApi
import com.example.core.data.model.product.MaterialBody
import com.example.core.data.model.product.ProductBody
import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.model.product.ProductModel
import com.example.core.domain.repos.ProductsAndMaterialsRepos
import io.ktor.client.call.body

internal class ProductsAndMaterialsReposImpl(
    private val materialApi: MaterialApi,
    private val productApi: ProductApi,
) : ProductsAndMaterialsRepos {
    override suspend fun getMaterials(): List<MaterialModel> {
        return materialApi.getMaterials().body<List<MaterialBody>>()
    }

    override suspend fun getProducts(): List<ProductModel> {
        return productApi.getProducts().body<List<ProductBody>>()
    }

}