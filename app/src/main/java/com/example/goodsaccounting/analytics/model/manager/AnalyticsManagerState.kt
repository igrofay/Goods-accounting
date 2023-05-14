package com.example.goodsaccounting.analytics.model.manager

import com.example.core.domain.model.analytics.ManagerAnalysisModel
import com.example.core.domain.model.product.AmountOfProductModel
import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.analytics.model.common.AmountOfProductCartEntry.Companion.fromListModelToChartEntryModel
import com.example.goodsaccounting.analytics.model.common.SellerIncomeCartEntry.Companion.fromListModelToChartEntryModel
import com.example.goodsaccounting.common.model.mvi.UIState
import com.patrykandpatrick.vico.core.entry.ChartEntryModel

internal sealed class AnalyticsManagerState : UIState(){
    object Load : AnalyticsManagerState()
    data class AnalyticsData(
        val currency: Currency,
        val soldToday: Float,
        val salesStatisticsForToday: ChartEntryModel,
        val salesStatisticsThisMonth: ChartEntryModel,
        val sellingProductToday: AmountOfProductModel,
        val productSalesForToday: ChartEntryModel,
        val productSalesThisMonth: ChartEntryModel,
        val isRefreshing: Boolean = false,
    ) : AnalyticsManagerState(){
        companion object{
            fun ManagerAnalysisModel.fromModelToAnalyticsData() = AnalyticsData(
                currency, soldToday,
                salesStatisticsForToday.fromListModelToChartEntryModel(),
                salesStatisticsThisMonth.fromListModelToChartEntryModel(),
                sellingProductToday,
                productSalesForToday.fromListModelToChartEntryModel(),
                productSalesThisMonth.fromListModelToChartEntryModel(),
            )
        }
    }
}