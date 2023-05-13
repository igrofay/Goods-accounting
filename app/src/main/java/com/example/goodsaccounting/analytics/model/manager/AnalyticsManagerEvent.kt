package com.example.goodsaccounting.analytics.model.manager

import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class AnalyticsManagerEvent : UIEvent(){
    object Refresh : AnalyticsManagerEvent()
}
