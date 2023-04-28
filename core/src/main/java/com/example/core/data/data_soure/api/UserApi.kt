package com.example.core.data.data_soure.api

import com.example.core.data.model.user.UpdateUserBody
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class UserApi(
    private val authorizedClient: HttpClient
) {
    suspend fun getUser() = authorizedClient.get("/api/profile")

    suspend fun update(userBody: UpdateUserBody) = authorizedClient.put("/api/profile"){
        setBody(userBody)
        contentType(ContentType.Application.Json)
    }
    suspend fun updateImage(byteArray: ByteArray) = authorizedClient.put("/api/profileIcon"){
        setBody(byteArray)
        contentType(ContentType.Image.Any)
    }

}