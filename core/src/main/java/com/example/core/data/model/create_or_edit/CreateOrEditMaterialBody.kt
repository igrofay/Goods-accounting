package com.example.core.data.model.create_or_edit

import com.example.core.domain.model.create_or_edit.CreateOrEditMaterialModel
import com.example.core.domain.model.product.Measurements
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateOrEditMaterialBody(
    override val name: String,
    override val measurement: Measurements,
    override val minimumQuantity: Float
) : CreateOrEditMaterialModel{
    companion object{
        fun CreateOrEditMaterialModel.fromModelToCreateMaterialBody() =
            CreateOrEditMaterialBody(name, measurement, minimumQuantity)
    }
}
