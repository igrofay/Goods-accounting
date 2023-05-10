package com.example.goodsaccounting.analytics.model.seller

import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class AnalyticsSellerEvent: UIEvent() {
    object Refresh: AnalyticsSellerEvent()
}
