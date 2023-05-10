package com.example.core.data.model.product

import com.example.core.domain.model.product.AmountOfProductModel
import kotlinx.serialization.Serializable

@Serializable
internal data class AmountOfProductBody(
    override val amount: Int,
    override val product: ProductBody
) : AmountOfProductModel
