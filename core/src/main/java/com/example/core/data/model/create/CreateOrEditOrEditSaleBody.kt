package com.example.core.data.model.create

import com.example.core.data.model.create.AmountOfIdBody.Companion.fromModelToAmountOfIdBody
import com.example.core.domain.model.create.CreateOrEditSaleModel
import com.example.core.domain.model.product.Currency
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateOrEditOrEditSaleBody(
    override val name: String,
    override val products: List<AmountOfIdBody>,
    override val checkPrice: Float,
    override val currency: Currency
) : CreateOrEditSaleModel{
    companion object{
        fun CreateOrEditSaleModel.fromModelToCreateOrEditOrEditSaleBody() = CreateOrEditOrEditSaleBody(
            name, products.map { it.fromModelToAmountOfIdBody() }, checkPrice, currency
        )
    }
}
