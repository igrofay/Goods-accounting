package com.example.goodsaccounting.analytics.view.seller

import android.graphics.Paint
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.resolveAsTypeface
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.analytics.model.common.AmountOfProductCartEntry
import com.example.goodsaccounting.analytics.model.common.IncomePerDateChartEntry
import com.example.goodsaccounting.analytics.model.common.SellerIncomeCartEntry
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.sf
import com.example.goodsaccounting.common.view.utils.getMonthName
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.marker.markerComponent
import com.patrykandpatrick.vico.compose.component.overlayingComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions
import com.patrykandpatrick.vico.core.entry.ChartEntryModel

@Composable
internal fun axisMonthFormatter(): AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    val res = LocalContext.current.resources
    return AxisValueFormatter { value, chartValues ->
        (chartValues.chartEntryModel.entries.first()
            .getOrNull(value.toInt()) as? IncomePerDateChartEntry)
            ?.localDate
            ?.run { getMonthName(monthNumber = monthNumber, res = res).take(3) }
            .orEmpty()
    }
}

internal fun axisDayFormatter(): AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    return AxisValueFormatter { value, chartValues ->
        (chartValues.chartEntryModel.entries.first()
            .getOrNull(value.toInt()) as? IncomePerDateChartEntry)
            ?.localDate
            ?.run { "$dayOfMonth" }
            .orEmpty()
    }
}

internal fun axisSellerNameFormatter() : AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    return AxisValueFormatter { value, chartValues ->
        (chartValues.chartEntryModel.entries.first()
            .getOrNull(value.toInt()) as? SellerIncomeCartEntry)
            ?.sellerName
            .orEmpty()
    }
}

internal fun axisProductNameFormatter() : AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    return AxisValueFormatter { value, chartValues ->
        (chartValues.chartEntryModel.entries.first()
            .getOrNull(value.toInt()) as? AmountOfProductCartEntry)
            ?.productName
            .orEmpty()
    }
}

@Composable
internal fun ColumnChartView(
    listEntry: ChartEntryModel,
    bottomFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom>,
    thickness: Dp = 20.dp,
) {
    val typeface by LocalFontFamilyResolver.current.resolveAsTypeface(fontFamily = sf)
    Chart(
        model = listEntry,
        chart = columnChart(
            columns = listOf(
                lineComponent(
                    color = MaterialTheme.colors.secondary,
                    thickness = thickness,
                    shape = MaterialTheme.shapes.small
                )
            ),
            spacing = 30.dp,
        ),
        startAxis = startAxis(
            label = axisLabelComponent(
                typeface = typeface,
            )
        ),
        bottomAxis = bottomAxis(
            valueFormatter = bottomFormatter,
            label = axisLabelComponent(
                typeface = typeface,
                lineCount = 3,
                textAlign = Paint.Align.CENTER
            )
        ),
        marker = markerComponent(
            label = textComponent {
                this.typeface = typeface
                this.padding = MutableDimensions(
                    0f,0f,0f, MaterialTheme.padding.small2.value
                )
            },
            indicator = overlayingComponent(
                outer = shapeComponent(),
                inner = shapeComponent(),
                innerPaddingAll = 10.dp
            ),
            guideline = lineComponent(thickness = 0.dp, color = Color.Transparent)
        )
    )
}