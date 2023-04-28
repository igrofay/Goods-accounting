package com.example.core.data.model.product

import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.ProductModel
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductBody(
    override val id: String,
    override val imageUrl: String?,
    override val name: String,
    override val price: Float,
    override val currency: Currency,
    override val materials: List<AmountOfMaterialBody>
) : ProductModel
