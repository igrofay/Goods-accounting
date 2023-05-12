package com.example.goodsaccounting.common.view.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.core.domain.model.product.AmountOfMaterialModel
import com.example.core.domain.model.product.ProductModel
import com.example.goodsaccounting.R
import com.patrykandpatrick.vico.core.extension.ceil
import kotlin.math.floor


@Composable
internal fun ProductModel.getProductComponents(): String {
    val res = LocalContext.current.resources
    return remember(this.materials) {
        val answer = mutableListOf<String>()
        for (material in this.materials) {
            val amount = if (floor(material.amount) == material.amount) {
                material.amount.toInt()
            } else {
                material.amount
            }.toString()
            answer.add(
                "${material.materialModel.name} - $amount ${
                    material.materialModel.measurement.getDesignation(
                        res
                    )
                }"
            )
        }
        answer.joinToString(", ")
    }
}

@Composable
internal fun List<AmountOfMaterialModel>.getAmountOfMaterialName(): String {
    val answer = mutableListOf<String>()
    for (amountOfMaterial in this) {
        val name = if (amountOfMaterial.materialModel.name.length > 30)
            amountOfMaterial.materialModel.name.take(30) + "…"
        else amountOfMaterial.materialModel.name
        val amount = if (amountOfMaterial.amount.ceil == amountOfMaterial.amount)
            amountOfMaterial.amount.toInt()
        else
            amountOfMaterial.amount
        answer.add(
            "$amount ${amountOfMaterial.materialModel.measurement.getDesignation()} $name"
        )
    }
    return answer.joinToString(", ")
}