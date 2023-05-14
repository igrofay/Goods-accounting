package com.example.goodsaccounting.analytics.model.common

import com.example.core.domain.model.product.AmountOfProductModel
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

internal class AmountOfProductCartEntry(
    val productName: String,
    override val x: Float,
    override val y: Float
) : ChartEntry {
    override fun withY(y: Float) = AmountOfProductCartEntry(productName, x, y)
    companion object{
        fun List<AmountOfProductModel>.fromListModelToChartEntryModel() = this
            .mapIndexed { index, model ->
                AmountOfProductCartEntry(
                    model.product.name, index.toFloat(), model.amount.toFloat()
                )
            }
            .let { ChartEntryModelProducer(it) }
            .getModel()
    }
}