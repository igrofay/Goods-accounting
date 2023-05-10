package com.example.core.data.data_soure.api

import android.util.Log
import com.example.core.data.model.create.CreateOrEditOrEditSaleBody
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

internal class SaleApi(
    private val authorizedClient: HttpClient
){
    suspend fun getSellerSales() = authorizedClient.get("/api/sales")

    suspend fun getSale(idSale: String) = authorizedClient.get("/api/saleAccounting/$idSale")
    suspend fun createSale(saleBody: CreateOrEditOrEditSaleBody) =
        authorizedClient.post("/api/salesAccounting"){
            setBody(saleBody)
            contentType(ContentType.Application.Json)
        }
    suspend fun updateImages(id: String, images: List<ByteArray>) =
        authorizedClient.put("/api/salesAccounting/$id") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        for (index in images.indices) {
                            append("document", images[index], Headers.build {
                                append(HttpHeaders.ContentType, "image/*")
                                append(HttpHeaders.ContentDisposition, "filename=image${index}")
                            })
                        }
                    }
                )
            )
            contentType(ContentType.Image.Any)
        }
    suspend fun editSale(idSale: String,saleBody: CreateOrEditOrEditSaleBody) =
        authorizedClient.put("/api/saleAccounting/$idSale"){
            setBody(saleBody)
            contentType(ContentType.Application.Json)
        }
    suspend fun getSaleImage(filename: String) = authorizedClient
        .get("/api/salesAccountingIcon/$filename")
}