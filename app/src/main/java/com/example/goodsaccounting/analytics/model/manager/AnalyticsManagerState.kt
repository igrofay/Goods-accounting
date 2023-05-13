package com.example.goodsaccounting.analytics.model.manager

import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.common.model.mvi.UIState

internal sealed class AnalyticsManagerState : UIState(){
    object Load : AnalyticsManagerState()
    data class AnalyticsData(
        val isRefreshing: Boolean = true,

    ) : AnalyticsManagerState()
}