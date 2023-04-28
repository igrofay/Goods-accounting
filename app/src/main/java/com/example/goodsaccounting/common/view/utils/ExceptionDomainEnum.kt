package com.example.goodsaccounting.common.view.utils

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.Measurements
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.R

@Composable
internal fun getStringRole(roleLevel: RoleLevel) : String {
    val res = LocalContext.current.resources
    return remember(roleLevel) {
        when(roleLevel){
            RoleLevel.None -> res.getString(R.string.under_consideration)
            RoleLevel.Seller -> res.getString(R.string.seller)
            RoleLevel.Manager ->  res.getString(R.string.manager)
            RoleLevel.Administrator -> res.getString(R.string.administrator)
        }
    }
}

internal fun Currency.getChar(): Char {
    return when(this){
        Currency.Rub -> '₽'
        Currency.Dollar -> '$'
        Currency.Euro -> '€'
    }
}

@Composable
internal fun Measurements.getDesignation() : String{
    val res = LocalContext.current.resources
    return remember(this) {
        this.getDesignation(res)
    }
}
internal fun Measurements.getDesignation(res : Resources) : String{
    return when(this){
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