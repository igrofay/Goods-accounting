package com.example.core.data.model.product

import com.example.core.domain.model.product.AmountOfMaterialModel
import com.example.core.domain.model.product.MaterialModel
import kotlinx.serialization.Serializable

@Serializable
internal data class AmountOfMaterialBody(
    override val amount: Float,
    override val materialModel: MaterialBody
) : AmountOfMaterialModel