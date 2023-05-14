package com.example.core.domain.use_case.analytics

import com.example.core.domain.repos.AnalyticsRepos

class GetManagerIncomeUseCase(
    private val analyticsRepos: AnalyticsRepos,
) {
    suspend fun execute() = runCatching {
        analyticsRepos.getManagerIncome()
    }
}