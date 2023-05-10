package com.example.core.domain.repos

import com.example.core.domain.model.analytics.SellerIncomeModel

interface AnalyticsRepos {
    suspend fun getSellerIncome() : SellerIncomeModel
}