package com.example.core.data.model.create

import com.example.core.domain.model.create.CreateMaterialModel
import com.example.core.domain.model.product.Measurements
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateMaterialBody(
    override val name: String,
    override val measurement: Measurements,
    override val minimumQuantity: Float
) : CreateMaterialModel{
    companion object{
        fun CreateMaterialModel.fromModelToCreateMaterialBody() =
            CreateMaterialBody(name, measurement, minimumQuantity)
    }
}
