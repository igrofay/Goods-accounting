package com.example.core.data.model.create_or_edit

import com.example.core.data.model.create_or_edit.AmountOfIdBody.Companion.fromModelToAmountOfIdBody
import com.example.core.domain.model.create_or_edit.CreateReceiptOrWriteOffMaterialModel
import kotlinx.serialization.Serializable


@Serializable
internal data class CreateReceiptOrWriteOffMaterialBody(
    override val name: String,
    override val listAmountOfMaterial: List<AmountOfIdBody>
): CreateReceiptOrWriteOffMaterialModel{
    companion object{
        fun CreateReceiptOrWriteOffMaterialModel.fromModelToCreateReceiptOrWriteOffMaterialBody() =
            CreateReceiptOrWriteOffMaterialBody(
                name, listAmountOfMaterial.map { it.fromModelToAmountOfIdBody() }
            )
    }
}
