package com.example.core.data.data_soure.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class AnalyticsApi(
    private val  authorizedClient: HttpClient,
){
    suspend fun getSellerAnalysis() = authorizedClient
        .get("/api/analytics/sales")
    suspend fun getManagerAnalysis() = authorizedClient
        .get("/api/analytics/sellers")
}