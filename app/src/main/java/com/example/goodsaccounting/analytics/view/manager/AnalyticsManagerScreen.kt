package com.example.goodsaccounting.analytics.view.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerEvent
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerState
import com.example.goodsaccounting.analytics.view_model.AnalyticsManagerVM
import com.example.goodsaccounting.common.view.state_view.LoadView
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun AnalyticsManagerScreen() {
    val analyticsManagerVM by rememberDIAwareViewModel<AnalyticsManagerVM>()
    val state by analyticsManagerVM.collectAsState()
    LaunchedEffect(Unit){
        analyticsManagerVM.onEvent(AnalyticsManagerEvent.Refresh)
    }
    when(val tState = state){
        is AnalyticsManagerState.AnalyticsData -> AnalyticsDataManagerView(
            state = tState,
            eventBase = analyticsManagerVM,
        )
        AnalyticsManagerState.Load -> LoadView()
    }
}