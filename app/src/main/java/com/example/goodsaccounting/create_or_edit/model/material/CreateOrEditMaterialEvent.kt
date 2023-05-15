package com.example.goodsaccounting.create_or_edit.model.material

import com.example.core.domain.model.product.Measurements
import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class CreateOrEditMaterialEvent : UIEvent(){
    class InputName(val name: String) : CreateOrEditMaterialEvent()
    class SelectMeasurements(val measurements: Measurements) : CreateOrEditMaterialEvent()
    class SelectImage(val uri: String) : CreateOrEditMaterialEvent()
    object CreateOrEdit: CreateOrEditMaterialEvent()
    class InputStringMinimumQuantity(val minimumQuantity: String) : CreateOrEditMaterialEvent()
}
