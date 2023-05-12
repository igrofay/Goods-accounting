package com.example.goodsaccounting.analytics.model.common

import com.example.core.domain.model.analytics.IncomePerDateModel
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class IncomePerDateChartEntry(
    val localDate: LocalDate,
    override val x: Float,
    override val y: Float,
): ChartEntry {
    override fun withY(y: Float) = IncomePerDateChartEntry(localDate, x, y)
    companion object{
        fun List<IncomePerDateModel>.fromListModelToChartEntryModel() = this
            .mapIndexed { index, model ->
                IncomePerDateChartEntry(
                    Instant.parse(model.date)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .date, index.toFloat(), model.income
                )
            }
            .let { ChartEntryModelProducer(it) }
            .getModel()
    }
}