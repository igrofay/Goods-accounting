package com.example.goodsaccounting.analytics.model.common

import com.example.core.domain.model.analytics.SellerIncomeModel
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

internal class SellerIncomeCartEntry(
    val sellerName: String,
    override val x: Float,
    override val y: Float
) : ChartEntry {
    override fun withY(y: Float) = SellerIncomeCartEntry(sellerName, x, y)
    companion object{
        fun List<SellerIncomeModel>.fromListModelToChartEntryModel() = this
            .mapIndexed { index, model ->
                SellerIncomeCartEntry(
                    model.sellerName, index.toFloat(), model.income
                )
            }
            .let { ChartEntryModelProducer(it) }
            .getModel()
    }
}