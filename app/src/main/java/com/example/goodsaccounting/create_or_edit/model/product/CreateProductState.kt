package com.example.goodsaccounting.create_or_edit.model.product

import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.MaterialModel
import com.example.goodsaccounting.common.model.mvi.UIState

internal data class CreateProductState(
    val imageUri: String? = null,
    val name: String = "",
    val textPrice: String = "",
    val isErrorPrice: Boolean = false,
    val currency: Currency = Currency.Rub,
    val materials: Map<String, String> = mapOf(), // id material to amount
    val isErrorAmountOfMaterial: Map<String, Boolean> = mapOf(), // id material to error
    val isCreating: Boolean = false,
    val isErrorName: Boolean = false,
    val materialsForAdd: Map<String, MaterialModel> = mapOf() // id material to material model
) :  UIState()
