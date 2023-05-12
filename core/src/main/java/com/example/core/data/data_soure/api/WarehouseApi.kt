package com.example.core.data.data_soure.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class WarehouseApi(
    private val authorizedClient: HttpClient,
) {
    suspend fun getListAmountOfMaterial() = authorizedClient
        .get("/api/warehouse/materials")
}