package com.example.core.data.model.create_or_edit

import com.example.core.data.model.create_or_edit.AmountOfIdBody.Companion.fromModelToAmountOfIdBody
import com.example.core.domain.model.create_or_edit.CreateOrEditProductModel
import com.example.core.domain.model.product.Currency
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateOrEditProductBody(
    override val name: String,
    override val price: Float,
    override val currency: Currency,
    override val materials: List<AmountOfIdBody>
) : CreateOrEditProductModel {
    companion object {
        internal fun CreateOrEditProductModel.fromModelToCreateOrEditProductBody() = CreateOrEditProductBody(
            name, price, currency, materials.map { it.fromModelToAmountOfIdBody() }
        )
    }
}