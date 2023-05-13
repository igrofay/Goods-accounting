package com.example.goodsaccounting.analytics.view.manager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerEvent
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerState
import com.example.goodsaccounting.analytics.model.seller.AnalyticsSellerEvent
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.utils.getCost
import com.example.goodsaccounting.common.view_model.EventBase

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun AnalyticsDataManagerView(
    state: AnalyticsManagerState.AnalyticsData,
    eventBase: EventBase<AnalyticsManagerEvent>
) {
    val refreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { eventBase.onEvent(AnalyticsManagerEvent.Refresh) }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = MaterialTheme.padding.medium2,
                vertical = MaterialTheme.padding.medium1
            ),
        ){
            item {
                Column(

                ) {
                    Text(
                        text = "Проданно за сегодня",
                        style = MaterialTheme.typography.subtitle1,
                    )
                    Text(
                        text = getCost(Currency.Rub, 100_000f),
                        style = MaterialTheme.typography.h6,
                    )
                }
            }
        }
        PullRefreshIndicator(
            state.isRefreshing,
            refreshState,
            Modifier.align(Alignment.TopCenter),
            contentColor = MaterialTheme.colors.primary
        )
    }
}