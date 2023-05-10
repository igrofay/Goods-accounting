package com.example.goodsaccounting.common.view.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.core.domain.model.sale.SaleModel
import com.example.goodsaccounting.R
//…
@Composable
internal fun SaleModel.getMaterialsName() : String {
    val answer = mutableListOf<String>()
    for (amountOfProduct in this.products){
        val name = if (amountOfProduct.product.name.length > 15)
            amountOfProduct.product.name
        else amountOfProduct.product.name
        answer.add(
            "${amountOfProduct.amount} ${stringResource(R.string.piece)} $name"
        )
    }
    return answer.joinToString(", ")
}