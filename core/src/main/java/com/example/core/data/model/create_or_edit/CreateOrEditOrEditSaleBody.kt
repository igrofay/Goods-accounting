package com.example.core.data.model.create_or_edit

import com.example.core.data.model.create_or_edit.AmountOfIdBody.Companion.fromModelToAmountOfIdBody
import com.example.core.domain.model.create_or_edit.CreateOrEditSaleModel
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
