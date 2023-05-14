package com.example.core.data.model.analytics

import com.example.core.domain.model.analytics.SellerIncomeAnalysisModel
import com.example.core.domain.model.product.Currency
import kotlinx.serialization.Serializable

@Serializable
internal data class SellerIncomeAnalysisBody(
    override val currency: Currency,
    override val earningForToday: Float,
    override val soldToday: Float,
    override val earningThisMonth: List<IncomePerDateBody>,
    override val monthlyEarnings: List<IncomePerDateBody>
) : SellerIncomeAnalysisModel
