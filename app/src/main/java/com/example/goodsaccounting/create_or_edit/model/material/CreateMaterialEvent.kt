package com.example.goodsaccounting.create_or_edit.model.material

import com.example.core.domain.model.product.Measurements
import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class CreateMaterialEvent : UIEvent(){
    class InputName(val name: String) : CreateMaterialEvent()
    class SelectMeasurements(val measurements: Measurements) : CreateMaterialEvent()
    class SelectImage(val uri: String) : CreateMaterialEvent()
    object Create: CreateMaterialEvent()
    class InputStringMinimumQuantity(val minimumQuantity: String) : CreateMaterialEvent()
}
