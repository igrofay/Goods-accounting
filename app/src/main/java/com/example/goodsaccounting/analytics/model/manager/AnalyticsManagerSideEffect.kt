package com.example.goodsaccounting.analytics.model.manager

import com.example.goodsaccounting.common.model.mvi.UISideEffect

internal sealed class AnalyticsManagerSideEffect : UISideEffect(){
    object Refresh : AnalyticsManagerSideEffect()
}
