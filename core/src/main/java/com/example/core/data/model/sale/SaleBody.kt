package com.example.core.data.model.sale

import com.example.core.data.model.product.AmountOfProductBody
import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.sale.SaleModel
import kotlinx.serialization.Serializable

@Serializable
internal data class SaleBody(
    override val name: String,
    override val date: String,
    override val imagesUrl: List<String>,
    override val products: List<AmountOfProductBody>,
    override val creatorName: String,
    override val id: String,
    override val checkPrice: Float,
    override val currency: Currency
): SaleModel
