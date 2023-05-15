package com.example.core.data.model.create_or_edit

import com.example.core.domain.model.create_or_edit.AmountOfIdModel
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
