package com.example.core.data.data_soure.api

import com.example.core.data.model.create_or_edit.CreateOrEditMaterialBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


internal class MaterialApi(
    private val authorizedClient: HttpClient,
) {

    suspend fun getMaterials() =
        authorizedClient.get("/api/materials")

    suspend fun createMaterial(createMaterialBody: CreateOrEditMaterialBody) =
        authorizedClient.post("/api/material") {
            setBody(createMaterialBody)
            contentType(ContentType.Application.Json)
        }

    suspend fun updateImageMaterial(byteArray: ByteArray, id: String) =
        authorizedClient.put("/api/materialIcon/$id") {
            setBody(byteArray)
            contentType(ContentType.Image.Any)
        }
    suspend fun getImageMaterial(filename: String) = authorizedClient
        .get("/api/materialIcon/$filename")
    suspend fun getMaterial(id: String) = authorizedClient
        .get("/api/material/$id")
    suspend fun editMaterial(id: String, createOrEditMaterialBody: CreateOrEditMaterialBody) = authorizedClient
        .put("/api/material/$id"){
            setBody(createOrEditMaterialBody)
            contentType(ContentType.Application.Json)
        }
}