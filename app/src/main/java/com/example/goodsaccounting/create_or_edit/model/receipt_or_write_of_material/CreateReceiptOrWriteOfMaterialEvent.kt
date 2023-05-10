package com.example.goodsaccounting.create_or_edit.model.receipt_or_write_of_material

import com.example.goodsaccounting.common.model.mvi.UIEvent

internal sealed class CreateReceiptOrWriteOfMaterialEvent : UIEvent(){
    object Create: CreateReceiptOrWriteOfMaterialEvent()
    class SelectImage(val imageUri: String, val position: Int) : CreateReceiptOrWriteOfMaterialEvent()
    class RemoveImage(val position: Int) : CreateReceiptOrWriteOfMaterialEvent()
    class InputName(val name: String) : CreateReceiptOrWriteOfMaterialEvent()
    class AddMaterial(val id:String): CreateReceiptOrWriteOfMaterialEvent()
    class RemoveMaterial(val id:String) : CreateReceiptOrWriteOfMaterialEvent()
    class InputAmountOfMaterial(val id: String, val amount: String) : CreateReceiptOrWriteOfMaterialEvent()
}
