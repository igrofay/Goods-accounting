package com.example.goodsaccounting.analytics.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerEvent
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerSideEffect
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerState
import com.example.goodsaccounting.common.view_model.AppVM
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container

internal class AnalyticsManagerVM(
    override val di: DI

) : AppVM<AnalyticsManagerState, AnalyticsManagerSideEffect, AnalyticsManagerEvent>(), DIAware{

    override val container: Container<AnalyticsManagerState, AnalyticsManagerSideEffect> =
        viewModelScope.container(
            AnalyticsManagerState.AnalyticsData()
        )

    override fun onEvent(event: AnalyticsManagerEvent) {

    }

    override fun onError(error: Throwable) {
        Log.e("AnalyticsManagerVM", error.message.toString())
    }

}