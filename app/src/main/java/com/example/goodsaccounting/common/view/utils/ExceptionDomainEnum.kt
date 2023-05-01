package com.example.goodsaccounting.common.view.utils

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.Measurements
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.R

@Composable
internal fun getStringRole(roleLevel: RoleLevel) : String {
    return when(roleLevel){
        RoleLevel.None -> stringResource(R.string.under_consideration)
        RoleLevel.Seller -> stringResource( R.string.seller)
        RoleLevel.Manager ->  stringResource(R.string.manager)
        RoleLevel.Administrator -> stringResource(R.string.administrator)
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
    return this.getDesignation(res)
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