package com.example.core.data.model.create

import com.example.core.data.model.create.AmountOfIdBody.Companion.fromModelToAmountOfIdBody
import com.example.core.domain.model.create.CreateProductModel
import com.example.core.domain.model.product.Currency
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateProductBody(
    override val name: String,
    override val price: Float,
    override val currency: Currency,
    override val materials: List<AmountOfIdBody>
) : CreateProductModel {
    companion object {
        internal fun CreateProductModel.fromModelToCreateProductBody() = CreateProductBody(
            name, price, currency, materials.map { it.fromModelToAmountOfIdBody() }
        )
    }
}