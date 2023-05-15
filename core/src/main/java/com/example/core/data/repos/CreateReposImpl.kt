package com.example.core.data.repos

import android.content.Context
import android.net.Uri
import com.example.core.data.data_soure.api.MaterialAccountingApi
import com.example.core.data.data_soure.api.MaterialApi
import com.example.core.data.data_soure.api.ProductApi
import com.example.core.data.data_soure.api.SaleApi
import com.example.core.data.model.create_or_edit.CreateOrEditMaterialBody.Companion.fromModelToCreateMaterialBody
import com.example.core.data.model.create_or_edit.CreateOrEditOrEditSaleBody.Companion.fromModelToCreateOrEditOrEditSaleBody
import com.example.core.data.model.create_or_edit.CreateOrEditProductBody.Companion.fromModelToCreateOrEditProductBody
import com.example.core.data.model.create_or_edit.CreateReceiptOrWriteOffMaterialBody.Companion.fromModelToCreateReceiptOrWriteOffMaterialBody
import com.example.core.data.model.create_or_edit.IdBody
import com.example.core.domain.model.create_or_edit.CreateOrEditMaterialModel
import com.example.core.domain.model.create_or_edit.CreateOrEditProductModel
import com.example.core.domain.model.create_or_edit.CreateReceiptOrWriteOffMaterialModel
import com.example.core.domain.model.create_or_edit.CreateOrEditSaleModel
import com.example.core.domain.model.create_or_edit.IdModel
import com.example.core.domain.model.error.SaleError
import com.example.core.domain.repos.CreateRepos
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.readBytes
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CreateReposImpl(
    private val materialApi: MaterialApi,
    private val productApi: ProductApi,
    private val materialAccountingApi: MaterialAccountingApi,
    private val saleApi: SaleApi,
    private val context: Context,
) : CreateRepos {
    override suspend fun createMaterial(createOrEditMaterialModel: CreateOrEditMaterialModel): IdModel {
        return materialApi.createMaterial(createOrEditMaterialModel.fromModelToCreateMaterialBody()).body<IdBody>()
    }


    override suspend fun createProduct(createOrEditProductModel: CreateOrEditProductModel): IdModel {
        return productApi.createProduct(createOrEditProductModel.fromModelToCreateOrEditProductBody()).body<IdBody>()
    }

    override suspend fun updateImageMaterial(imageUri: String, id: String) {
        withContext(Dispatchers.IO){
            val byteArray =if (imageUri.contains("http")){
                val filename = imageUri.split("/").last()
                materialApi.getImageMaterial(filename).readBytes()
            }else{
                val stream = context.contentResolver.openInputStream(Uri.parse(imageUri))!!
                stream.readBytes().apply {
                    stream.close()
                }
            }
            materialApi.updateImageMaterial(byteArray,id)
        }
    }

    override suspend fun updateImageProduct(imageUri: String, id: String) {
        withContext(Dispatchers.IO){
            val byteArray = if (imageUri.contains("http")){
                val filename = imageUri.split("/").last()
                productApi.getImageProduct(filename).readBytes()
            }else{
                val stream = context.contentResolver.openInputStream(Uri.parse(imageUri))!!
                stream.readBytes().apply {
                    stream.close()
                }
            }
            productApi.updateImageProduct(byteArray,id)
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

    override suspend fun createSale(createOrEditSaleModel: CreateOrEditSaleModel): IdModel{
        try {
            return saleApi.createSale(
                createOrEditSaleModel.fromModelToCreateOrEditOrEditSaleBody()
            ).body<IdBody>()
        }catch (e: ClientRequestException){
            throw when(e.response.status){
                BadRequest-> SaleError.ThereAreFewerMaterialsInStock
                else-> e
            }
        }

    }


    override suspend fun updateImagesSale(listImageUri: List<String>, id: String) {
        withContext(Dispatchers.IO){
            val listByteArray = mutableListOf<ByteArray>()
            for (imageUri in listImageUri){
                if (imageUri.contains("http")){
                    val filename = imageUri.split("/").last()
                    listByteArray.add(
                        saleApi.getImageSale(filename).readBytes()
                    )
                }else{
                    val stream = context.contentResolver.openInputStream(Uri.parse(imageUri))!!
                    listByteArray.add(stream.readBytes())
                    stream.close()
                }
            }
            saleApi.updateImages(id, listByteArray)
        }
    }


}