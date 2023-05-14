package com.example.core.data.model.analytics

import com.example.core.domain.model.analytics.SellerIncomeModel
import kotlinx.serialization.Serializable

@Serializable
internal data class SellerIncomeBody(
    override val sellerName: String,
    override val income: Float
) : SellerIncomeModel
