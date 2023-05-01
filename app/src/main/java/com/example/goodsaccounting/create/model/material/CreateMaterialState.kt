package com.example.goodsaccounting.create.model.material

import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.model.product.Measurements
import com.example.goodsaccounting.common.model.UIState

internal data class CreateMaterialState(
    override val id: String = "",
    override val name: String = "",
    val isErrorName: Boolean = false,
    override val measurement: Measurements = Measurements.Other,
    override val imageUrl: String? = null,
    val isCreating: Boolean = false,
) : MaterialModel, UIState()
