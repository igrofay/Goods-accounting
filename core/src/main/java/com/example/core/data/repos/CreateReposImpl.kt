package com.example.core.data.repos

import android.content.Context
import android.net.Uri
import com.example.core.data.data_soure.api.MaterialAccountingApi
import com.example.core.data.data_soure.api.MaterialApi
import com.example.core.data.data_soure.api.ProductApi
import com.example.core.data.model.create.CreateMaterialBody.Companion.fromModelToCreateMaterialBody
import com.example.core.data.model.create.CreateProductBody.Companion.fromModelToCreateProductBody
import com.example.core.data.model.create.CreateReceiptOrWriteOffMaterialBody.Companion.fromModelToCreateReceiptOrWriteOffMaterialBody
import com.example.core.data.model.create.IdBody
import com.example.core.data.model.product.MaterialBody.Companion.fromModelToMaterialBody
import com.example.core.domain.model.create.CreateMaterialModel
import com.example.core.domain.model.create.CreateProductModel
import com.example.core.domain.model.create.CreateReceiptOrWriteOffMaterialModel
import com.example.core.domain.model.create.CreateSaleModel
import com.example.core.domain.model.create.IdModel
import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.repos.CreateRepos
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CreateReposImpl(
    private val materialApi: MaterialApi,
    private val productApi: ProductApi,
    private val materialAccountingApi: MaterialAccountingApi,
    private val context: Context,
) : CreateRepos {
    override suspend fun createMaterial(createMaterialModel: CreateMaterialModel): IdModel {
        return materialApi.createMaterial(createMaterialModel.fromModelToCreateMaterialBody()).body<IdBody>()
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

    override suspend fun createReceiptOrWriteOffMaterial(
        isReceipt: Boolean,
        createReceiptOrWriteOffMaterialModel: CreateReceiptOrWriteOffMaterialModel
    ): IdModel {
        return if (isReceipt){
            materialAccountingApi.receiptMaterial(
                createReceiptOrWriteOffMaterialModel.fromModelToCreateReceiptOrWriteOffMaterialBody()
            ).body<IdBody>()
        }else{
            materialAccountingApi.writeOffMaterial(
                createReceiptOrWriteOffMaterialModel.fromModelToCreateReceiptOrWriteOffMaterialBody()
            ).body<IdBody>()
        }
    }

    override suspend fun updateImagesReceiptOrWriteOffMaterial(
        listImageUri: List<String>,
        id: String
    ) {
        withContext(Dispatchers.IO){
            val listByteArray = mutableListOf<ByteArray>()
            for (imageUri in listImageUri){
                val stream = context.contentResolver.openInputStream(Uri.parse(imageUri))!!
                listByteArray.add(stream.readBytes())
                stream.close()
            }
            materialAccountingApi.updateImages(id, listByteArray)
        }
    }

    override suspend fun createSale(createSaleModel: CreateSaleModel): IdModel {
        TODO("Not yet implemented")
    }

    override suspend fun updateImagesSale(listImageUri: List<String>, id: String) {
        withContext(Dispatchers.IO){
            val listByteArray = mutableListOf<ByteArray>()
            for (imageUri in listImageUri){
                val stream = context.contentResolver.openInputStream(Uri.parse(imageUri))!!
                listByteArray.add(stream.readBytes())
                stream.close()
            }
            // TODO
        }
    }


}