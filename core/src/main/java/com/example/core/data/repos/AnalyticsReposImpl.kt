package com.example.core.data.repos

import android.util.Log
import com.example.core.data.data_soure.api.AnalyticsApi
import com.example.core.data.model.analytics.SellerIncomeBody
import com.example.core.domain.model.analytics.SellerIncomeModel
import com.example.core.domain.repos.AnalyticsRepos
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText

internal class AnalyticsReposImpl(
    private val analyticsApi: AnalyticsApi
): AnalyticsRepos {
    override suspend fun getSellerIncome(): SellerIncomeModel {
        val s =analyticsApi
            .getAnalyticSeller()
        Log.e("AnalyticsReposImpl", s.bodyAsText())
        return s.body<SellerIncomeBody>()
    }

}