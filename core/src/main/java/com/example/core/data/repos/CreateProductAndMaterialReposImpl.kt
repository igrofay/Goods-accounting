package com.example.core.data.repos

import android.content.Context
import android.net.Uri
import com.example.core.data.data_soure.api.MaterialApi
import com.example.core.data.data_soure.api.ProductApi
import com.example.core.data.model.create.CreateProductBody.Companion.fromModelToCreateProductBody
import com.example.core.data.model.create.IdBody
import com.example.core.data.model.product.MaterialBody.Companion.fromModelToMaterialBody
import com.example.core.domain.model.create.CreateProductModel
import com.example.core.domain.model.create.IdModel
import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.repos.CreateProductAndMaterialRepos
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CreateProductAndMaterialReposImpl(
    private val materialApi: MaterialApi,
    private val productApi: ProductApi,
    private val context: Context,
) : CreateProductAndMaterialRepos {
    override suspend fun createMaterial(materialModel: MaterialModel): IdModel {
        return materialApi.createMaterial(materialModel.fromModelToMaterialBody()).body<IdBody>()
    }

    override suspend fun createProduct(createProductModel: CreateProductModel): IdModel {
        return productApi.createProduct(createProductModel.fromModelToCreateProductBody()).body<IdBody>()
    }

    override suspend fun updateImageMaterial(imageUri: String, id: String) {
        withContext(Dispatchers.IO){
            val stream = context.contentResolver.openInputStream(Uri.parse(imageUri))!!
            materialApi.updateImageMaterial(stream.readBytes(),id)
            stream.close()
        }
    }

    override suspend fun updateImageProduct(imageUri: String, id: String) {
        withContext(Dispatchers.IO){
            val stream = context.contentResolver.openInputStream(Uri.parse(imageUri))!!
            productApi.updateImageProduct(stream.readBytes(),id)
            stream.close()
        }
    }
}