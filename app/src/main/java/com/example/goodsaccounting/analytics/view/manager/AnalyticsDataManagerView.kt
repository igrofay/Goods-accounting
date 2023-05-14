@file:OptIn(ExperimentalFoundationApi::class)

package com.example.goodsaccounting.analytics.view.manager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.R
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerEvent
import com.example.goodsaccounting.analytics.model.manager.AnalyticsManagerState
import com.example.goodsaccounting.analytics.view.seller.ColumnChartView
import com.example.goodsaccounting.analytics.view.seller.axisProductNameFormatter
import com.example.goodsaccounting.analytics.view.seller.axisSellerNameFormatter
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.utils.getCost
import com.example.goodsaccounting.common.view.utils.getCurrentMonth
import com.example.goodsaccounting.common.view_model.EventBase

@Composable
@OptIn(ExperimentalMaterialApi::class)
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
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.medium2),
        ){
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        MaterialTheme.padding.small2
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.sold_today),
                        style = MaterialTheme.typography.subtitle1,
                    )
                    Text(
                        text = getCost(state.currency, state.soldToday),
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
                        text = stringResource(R.string.sales_statistics_for_today),
                        style = MaterialTheme.typography.subtitle1,
                    )
                    ColumnChartView(
                        listEntry = state.salesStatisticsForToday,
                        bottomFormatter = axisSellerNameFormatter(),
                        thickness = 50.dp,
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

                        text = stringResource(R.string.sales_statistics_for) +" "+ getCurrentMonth(),
                        style = MaterialTheme.typography.subtitle1,
                    )
                    ColumnChartView(
                        listEntry = state.salesStatisticsThisMonth,
                        bottomFormatter = axisSellerNameFormatter(),
                        thickness = 50.dp,
                    )
                }
            }
            item {
                Divider()
            }
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        MaterialTheme.padding.medium1
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.selling_product_today),
                        style = MaterialTheme.typography.subtitle1,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement
                            .spacedBy(MaterialTheme.padding.medium1),
                    ){
                        CustomImage(
                            image = state.sellingProductToday.product.imageUrl,
                            modifier = Modifier
                                .height(60.dp)
                                .width(100.dp)
                                .shadow(1.dp)
                        )
                        Text(
                            text =  state.sellingProductToday.product.name,
                            maxLines = 3,
                            modifier = Modifier.weight(2f),
                            style = MaterialTheme.typography.subtitle1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text =  state.sellingProductToday.amount.toString() ,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .weight(1.25f)
                                .basicMarquee(),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                        )
                    }
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
                        text = stringResource(R.string.product_sales_for_today),
                        style = MaterialTheme.typography.subtitle1,
                    )
                    ColumnChartView(
                        listEntry = state.productSalesForToday,
                        bottomFormatter = axisProductNameFormatter(),
                        thickness = 50.dp,
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
                        text =  stringResource(R.string.product_sales_for) + " "+ getCurrentMonth(),
                        style = MaterialTheme.typography.subtitle1,
                    )
                    ColumnChartView(
                        listEntry = state.productSalesThisMonth,
                        bottomFormatter = axisProductNameFormatter(),
                        thickness = 50.dp,
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