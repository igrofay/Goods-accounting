package com.example.goodsaccounting.create_or_edit.model.material

import com.example.core.domain.model.create_or_edit.CreateOrEditMaterialModel
import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.model.product.Measurements
import com.example.goodsaccounting.common.model.mvi.UIState
import com.example.goodsaccounting.create_or_edit.model.common.CreateOrEditState
import com.example.goodsaccounting.create_or_edit.model.utils.floatToString
import com.example.goodsaccounting.create_or_edit.model.utils.stringToFloat

internal sealed class CreateOrEditMaterialState : UIState(){

    object Load: CreateOrEditMaterialState()
    data class CreateOrEdit(
        val createOrEditState: CreateOrEditState,
        override val name: String = "",
        val isErrorName: Boolean = false,
        override val measurement: Measurements = Measurements.Other,
        val stringMinimumQuantity: String = "",
        val isErrorMinimumQuantity: Boolean = false,
        val imageUrl: String? = null,
        val isCreatingOrEdit: Boolean = false,
    ) : CreateOrEditMaterialModel, CreateOrEditMaterialState(){
        override val minimumQuantity: Float
            get() = stringToFloat(stringMinimumQuantity)
        companion object{
            fun MaterialModel.fromModelToCreateOrEdit() = CreateOrEdit(
                CreateOrEditState.Edit,
                name = name,
                measurement = measurement,
                stringMinimumQuantity = floatToString(minimumQuantity),
                imageUrl = imageUrl
            )
        }
    }
}
