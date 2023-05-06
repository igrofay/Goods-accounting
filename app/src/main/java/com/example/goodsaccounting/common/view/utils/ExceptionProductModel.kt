package com.example.goodsaccounting.common.view.utils

import android.icu.text.NumberFormat
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.ProductModel
import kotlin.math.floor
import android.icu.util.Currency as AndroidCurrency


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
                "${material.materialModel.name} - $amount ${material.materialModel.measurement.getDesignation(res)}"
            )
        }
        answer.joinToString(", ")
    }
}