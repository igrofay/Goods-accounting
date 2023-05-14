package com.example.core.domain.repos

import com.example.core.domain.model.analytics.ManagerAnalysisModel
import com.example.core.domain.model.analytics.SellerIncomeAnalysisModel

interface AnalyticsRepos {
    suspend fun getSellerIncome() : SellerIncomeAnalysisModel

    suspend fun getManagerIncome() : ManagerAnalysisModel
}