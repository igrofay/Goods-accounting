package com.example.goodsaccounting.analytics.model.seller

import com.example.core.domain.model.analytics.SellerIncomeModel
import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.analytics.model.common.IncomePerDateChartEntry.Companion.fromListModelToChartEntryModel
import com.example.goodsaccounting.analytics.model.seller.AnalyticsSellerState.AnalyticsData.Companion.fromModelToAnalyticsData
import com.example.goodsaccounting.common.model.mvi.UIState
import com.patrykandpatrick.vico.core.entry.ChartEntryModel

internal sealed class AnalyticsSellerState : UIState(){
    object Loading : AnalyticsSellerState()
    data class AnalyticsData(
        val currency: Currency,
        val earningForToday: Float,
        val soldToday: Float,
        val earningThisMonth: ChartEntryModel,
        val monthlyEarnings: ChartEntryModel,
        val isRefreshing : Boolean = false,
    ) : AnalyticsSellerState(){
        companion object{
            fun SellerIncomeModel.fromModelToAnalyticsData() = AnalyticsData(
                currency,earningForToday, soldToday,
                earningThisMonth.fromListModelToChartEntryModel(),
                monthlyEarnings.fromListModelToChartEntryModel()
            )
        }
    }
}
