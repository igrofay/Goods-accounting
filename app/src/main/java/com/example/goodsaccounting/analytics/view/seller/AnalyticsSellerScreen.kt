package com.example.goodsaccounting.analytics.view.seller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.example.goodsaccounting.analytics.model.seller.AnalyticsSellerEvent
import com.example.goodsaccounting.analytics.model.seller.AnalyticsSellerState
import com.example.goodsaccounting.analytics.view_model.AnalyticsSellerVM
import com.example.goodsaccounting.common.view.state_view.LoadView
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun AnalyticsSellerScreen() {
    val analyticsSellerVM by rememberDIAwareViewModel<AnalyticsSellerVM>()
    val state by analyticsSellerVM.collectAsState()
    LaunchedEffect(Unit){
        analyticsSellerVM.onEvent(AnalyticsSellerEvent.Refresh)
    }
    when(val tState =state){
        AnalyticsSellerState.Loading -> LoadView()
        is AnalyticsSellerState.AnalyticsData -> AnalyticsDataSellerView(
            state = tState,
            eventBase = analyticsSellerVM,
        )
    }
}