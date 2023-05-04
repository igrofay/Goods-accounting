package com.example.core.data.data_soure.api

import com.example.core.data.model.create.CreateProductBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class ProductApi(
    private val authorizedClient: HttpClient,
) {

    suspend fun getProducts() =
        authorizedClient.get("/api/products")

    suspend fun createProduct(productBody: CreateProductBody) = authorizedClient
        .post("api/product") {
            setBody(productBody)
            contentType(ContentType.Application.Json)
        }

    suspend fun updateImageProduct(byteArray: ByteArray, id: String) = authorizedClient
        .put("api/productIcon/$id") {
            setBody(byteArray)
            contentType(ContentType.Image.Any)
        }
}