package com.example.core.data.data_soure.api

import android.util.Log
import com.example.core.data.model.create_or_edit.CreateReceiptOrWriteOffMaterialBody
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

internal class MaterialAccountingApi(
    private val authorizedClient: HttpClient,
) {
    suspend fun receiptMaterial(createReceiptOrWriteOffMaterialBody: CreateReceiptOrWriteOffMaterialBody) =
        authorizedClient.post("/api/materialAccounting/receipt") {
            setBody(createReceiptOrWriteOffMaterialBody)
            contentType(ContentType.Application.Json)
        }

    suspend fun writeOffMaterial(createReceiptOrWriteOffMaterialBody: CreateReceiptOrWriteOffMaterialBody) =
        authorizedClient.post("/api/materialAccounting/writeOff") {
            setBody(createReceiptOrWriteOffMaterialBody)
            contentType(ContentType.Application.Json)
        }

    suspend fun updateImages(id: String, images: List<ByteArray>) =
        authorizedClient.put("/api/materialAccounting/$id") {
            Log.d("updateImages", images.size.toString())
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
    suspend fun getReceiptMaterial() =
        authorizedClient.get("/api/materialAccounting/receipt")
    suspend fun getWriteOffMaterial() =
        authorizedClient.get("/api/materialAccounting/write-off")
}