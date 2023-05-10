package com.example.goodsaccounting.create_or_edit.model.material

import com.example.core.domain.model.create.CreateMaterialModel
import com.example.core.domain.model.product.Measurements
import com.example.goodsaccounting.common.model.mvi.UIState
import com.example.goodsaccounting.create_or_edit.model.utils.stringToFloat

internal data class CreateMaterialState(
    override val name: String = "",
    val isErrorName: Boolean = false,
    override val measurement: Measurements = Measurements.Other,
    val stringMinimumQuantity: String = "",
    val isErrorMinimumQuantity: Boolean = false,
    val imageUrl: String? = null,
    val isCreating: Boolean = false,
) : CreateMaterialModel, UIState(){
    override val minimumQuantity: Float
        get() = stringToFloat(stringMinimumQuantity)
}
