package com.example.core.data.model.analytics

import com.example.core.data.model.product.AmountOfProductBody
import com.example.core.domain.model.analytics.ManagerAnalysisModel
import com.example.core.domain.model.product.AmountOfProductModel
import com.example.core.domain.model.product.Currency
import kotlinx.serialization.Serializable

@Serializable
internal data class ManagerAnalysisBody(
    override val currency: Currency,
    override val soldToday: Float,
    override val salesStatisticsForToday: List<SellerIncomeBody>,
    override val salesStatisticsThisMonth: List<SellerIncomeBody>,
    override val sellingProductToday: AmountOfProductBody,
    override val productSalesForToday: List<AmountOfProductBody>,
    override val productSalesThisMonth: List<AmountOfProductBody>
) : ManagerAnalysisModel
