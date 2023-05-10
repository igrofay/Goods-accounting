package com.example.core.domain.use_case.analytics

import com.example.core.domain.repos.AnalyticsRepos

class GetSellerIncomeUseCase(
    private val analyticsRepos: AnalyticsRepos,
) {
    suspend fun execute() = runCatching {
        analyticsRepos.getSellerIncome()
    }
}