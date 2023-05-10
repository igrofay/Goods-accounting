package com.example.core.data.model.analytics

import com.example.core.domain.model.analytics.IncomePerDateModel
import kotlinx.serialization.Serializable

@Serializable
internal data class IncomePerDateBody(
    override val date: String,
    override val income: Float
): IncomePerDateModel
