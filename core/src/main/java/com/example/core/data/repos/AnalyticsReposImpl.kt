package com.example.core.data.repos

import android.util.Log
import com.example.core.data.data_soure.api.AnalyticsApi
import com.example.core.data.model.analytics.ManagerAnalysisBody
import com.example.core.data.model.analytics.SellerIncomeAnalysisBody
import com.example.core.domain.model.analytics.ManagerAnalysisModel
import com.example.core.domain.model.analytics.SellerIncomeAnalysisModel
import com.example.core.domain.repos.AnalyticsRepos
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText

internal class AnalyticsReposImpl(
    private val analyticsApi: AnalyticsApi
): AnalyticsRepos {
    override suspend fun getSellerIncome() = analyticsApi
        .getSellerAnalysis().body<SellerIncomeAnalysisBody>()

    override suspend fun getManagerIncome() = analyticsApi
        .getManagerAnalysis().body<ManagerAnalysisBody>()

}