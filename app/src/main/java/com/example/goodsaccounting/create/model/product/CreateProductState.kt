package com.example.goodsaccounting.create.model.product

import com.example.core.domain.model.create.AmountOfIdModel
import com.example.core.domain.model.create.CreateProductModel
import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.common.model.UIState

internal data class CreateProductState(
    val imageUri: String? = null,
    override val name: String = "",
    override val price: Float = 0f,
    override val currency: Currency = Currency.Rub,
    override val materials: List<AmountOfIdModel> = listOf(),
    val isCreating: Boolean = false
) : CreateProductModel, UIState()
