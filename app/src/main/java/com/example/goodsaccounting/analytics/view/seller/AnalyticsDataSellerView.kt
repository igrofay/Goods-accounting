package com.example.goodsaccounting.analytics.view.seller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.analytics.model.seller.AnalyticsSellerEvent
import com.example.goodsaccounting.analytics.model.seller.AnalyticsSellerState
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.utils.getCost
import com.example.goodsaccounting.common.view.utils.getCurrentMonth
import com.example.goodsaccounting.common.view_model.EventBase
import kotlinx.datetime.LocalDate


@Composable
@OptIn(ExperimentalMaterialApi::class)
internal fun AnalyticsDataSellerView(
    state: AnalyticsSellerState.AnalyticsData,
    eventBase: EventBase<AnalyticsSellerEvent>
) {
    val refreshState = rememberPullRefreshState(refreshing = state.isRefreshing, onRefresh = { eventBase.onEvent(AnalyticsSellerEvent.Refresh) })
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.your_statistics),
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = MaterialTheme.padding.medium2)
        )
        Box(
            modifier = Modifier.weight(1f)
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(refreshState),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.medium2),
                contentPadding = PaddingValues(
                    horizontal = MaterialTheme.padding.medium2,
                    vertical = MaterialTheme.padding.medium1
                ),
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            MaterialTheme.padding.small2
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.income_for_today),
                            style = MaterialTheme.typography.subtitle1,
                        )
                        Text(
                            text = stringResource(R.string.was_sold_for_amount),
                            style = MaterialTheme.typography.body1,
                        )
                        Text(
                            text = getCost(state.currency, state.soldToday),
                            style = MaterialTheme.typography.h6,
                        )
                        Divider()
                        Text(
                            text = stringResource(R.string.of_which_earned),
                            style = MaterialTheme.typography.body1,
                        )
                        Text(
                            text = getCost(state.currency, state.earningForToday),
                            style = MaterialTheme.typography.h6,
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(
                            MaterialTheme.padding.medium1
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.earnings_statistics_for) +" "+ getCurrentMonth(),
                            style = MaterialTheme.typography.subtitle1,
                        )
                        ColumnChartView(
                            listEntry = state.earningThisMonth,
                            bottomFormatter = axisDayFormatter()
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(
                            MaterialTheme.padding.medium1
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.monthly_earning_statistics),
                            style = MaterialTheme.typography.subtitle1,
                        )
                        ColumnChartView(
                            listEntry = state.monthlyEarnings,
                            bottomFormatter = axisMonthFormatter(),
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

}