package com.example.goodsaccounting.common.view.utils

import android.icu.text.NumberFormat
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.ProductModel
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt
import android.icu.util.Currency as AndroidCurrency

@Composable
internal fun ProductModel.getProductCost() : String {
    return remember(this.currency, this.price) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val numberFormat = NumberFormat.getCurrencyInstance().apply {
                currency = AndroidCurrency.getInstance(
                    this@getProductCost.currency.isoCode
                )
            }
            numberFormat.format(this.price)
        }else {
            "${this.price} ${this.currency.getChar()}"
        }
    }
}

@Composable
internal fun ProductModel.getProductComponents() : String{
    val res = LocalContext.current.resources
    return remember(this.materials) {
        val answer = mutableListOf<String>()
        for (material in this.materials){
            val amount =  if (floor(material.amount) == material.amount){
                material.amount.toInt()
            }else {
                material.amount
            }.toString()
            answer.add(
                "${material.materialModel.name} - $amount ${material.materialModel.measurements.getDesignation(res)}"
            )
        }
        answer.joinToString(", ")
    }
}