package com.example.goodsaccounting.common.view.utils

import android.content.res.Resources
import android.icu.text.NumberFormat
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.Measurements
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.R
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import android.icu.util.Currency as AndroidCurrency

@Composable
internal fun getStringRole(roleLevel: RoleLevel): String {
    return when (roleLevel) {
        RoleLevel.None -> stringResource(R.string.under_consideration)
        RoleLevel.Seller -> stringResource(R.string.seller)
        RoleLevel.Manager -> stringResource(R.string.manager)
        RoleLevel.Administrator -> stringResource(R.string.administrator)
    }
}

internal fun Currency.getChar(): Char {
    return when (this) {
        Currency.Rub -> '₽'
//        Currency.Dollar -> '$'
//        Currency.Euro -> '€'
    }
}

@Composable
internal fun Currency.getDesignation(): String {
    return when (this) {
        Currency.Rub -> stringResource(R.string.rubles)
//        Currency.Dollar ->  stringResource(R.string.dollars)
//        Currency.Euro -> stringResource(R.string.euro)
    }
}

@Composable
internal fun Measurements.getDesignation(): String {
    val res = LocalContext.current.resources
    return this.getDesignation(res)
}

internal fun Measurements.getDesignation(res: Resources): String {
    return when (this) {
        Measurements.Piece -> res.getString(R.string.piece)
        Measurements.Kilogram -> res.getString(R.string.kilogram)
        Measurements.Gram -> res.getString(R.string.gram)
        Measurements.Liter -> res.getString(R.string.liter)
        Measurements.Milliliter -> res.getString(R.string.milliliter)
        Measurements.Meter -> res.getString(R.string.mater)
        Measurements.Centimeter -> res.getString(R.string.centimeter)
        Measurements.Other -> res.getString(R.string.other)
    }
}

@Composable
internal fun getCost(currencyData: Currency, cost: Float): String {
    return remember(currencyData, cost) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val numberFormat = NumberFormat.getCurrencyInstance().apply {
                currency = AndroidCurrency.getInstance(
                    currencyData.isoCode
                )
            }
            numberFormat.format(cost)
        } else {
            "$cost ${currencyData.getChar()}"
        }
    }
}

//@Composable
//internal fun getMonthName(monthNumber: Int): String {
//    return when (monthNumber) {
//        1 -> stringResource(R.string.jan)
//        2 -> stringResource(R.string.feb)
//        3 -> stringResource(R.string.mar)
//        4 -> stringResource(R.string.apr)
//        5 -> stringResource(R.string.may)
//        6 -> stringResource(R.string.jun)
//        7 -> stringResource(R.string.jul)
//        8 -> stringResource(R.string.aug)
//        9 -> stringResource(R.string.sep)
//        10 -> stringResource(R.string.oct)
//        11 -> stringResource(R.string.nov)
//        12 -> stringResource(R.string.dec)
//        else -> ""
//    }
//}
internal fun getMonthName(res: Resources, monthNumber: Int) : String{
    return when (monthNumber) {
        1 -> res.getString(R.string.jan)
        2 -> res.getString(R.string.feb)
        3 -> res.getString(R.string.mar)
        4 -> res.getString(R.string.apr)
        5 -> res.getString(R.string.may)
        6 -> res.getString(R.string.jun)
        7 -> res.getString(R.string.jul)
        8 -> res.getString(R.string.aug)
        9 -> res.getString(R.string.sep)
        10 -> res.getString(R.string.oct)
        11 -> res.getString(R.string.nov)
        12 -> res.getString(R.string.dec)
        else -> ""
    }
}

@Composable
internal fun getCurrentMonth() : String{
    val res = LocalContext.current.resources
    val month = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date.monthNumber
    return getMonthName(res, month)
}

internal fun getDateString(dateIso: String) = Instant.parse(dateIso)
    .toLocalDateTime(TimeZone.currentSystemDefault())
    .date.toString()

internal fun Float.transformationToString() : String{
    return String.format("%.2f", this)
}