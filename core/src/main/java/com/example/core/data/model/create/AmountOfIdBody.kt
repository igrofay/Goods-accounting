package com.example.core.data.model.create

import com.example.core.domain.model.create.AmountOfIdModel
import kotlinx.serialization.Serializable

@Serializable
internal data class AmountOfIdBody(
    override val id: String,
    override val amount: Float
) : AmountOfIdModel {
    companion object{
        internal fun AmountOfIdModel.fromModelToAmountOfIdBody() =
            AmountOfIdBody(id, amount)
    }
}
