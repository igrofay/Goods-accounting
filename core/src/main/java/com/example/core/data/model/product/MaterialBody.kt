package com.example.core.data.model.product

import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.model.product.Measurements
import kotlinx.serialization.Serializable

@Serializable
internal data class MaterialBody(
    override val id: String,
    override val name: String,
    override val measurement: Measurements,
    override val imageUrl: String?
) : MaterialModel{
    companion object{
        internal fun MaterialModel.fromModelToMaterialBody() = MaterialBody(
            id, name, measurement, imageUrl
        )
    }
}

