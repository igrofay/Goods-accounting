package com.example.goodsaccounting.create_or_edit.model.product

import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.model.product.ProductModel
import com.example.goodsaccounting.common.model.mvi.UIState
import com.example.goodsaccounting.create_or_edit.model.common.CreateOrEditState
import com.example.goodsaccounting.create_or_edit.model.utils.floatToString

internal sealed class CreateOrEditProductState : UIState(){
    object Load : CreateOrEditProductState()
    data class CreateOrEdit(
        val createOrEditState: CreateOrEditState,
        val imageUri: String? = null,
        val name: String = "",
        val textPrice: String = "",
        val isErrorPrice: Boolean = false,
        val currency: Currency = Currency.Rub,
        val materials: Map<String, String> = mapOf(), // id material to amount
        val isErrorAmountOfMaterial: Map<String, Boolean> = mapOf(), // id material to error
        val isCreating: Boolean = false,
        val isErrorName: Boolean = false,
        val materialsForAdd: Map<String, MaterialModel> = mapOf() // id material to material model
    ) :  CreateOrEditProductState(){
        companion object{
            fun ProductModel.fromModelToCreateOrEdit(materialsForAdd: Map<String, MaterialModel>) = CreateOrEdit(
                CreateOrEditState.Edit,
                imageUri = imageUrl,
                name = name,
                textPrice = floatToString(price),
                currency = currency,
                materials = materials.associate {
                    it.materialModel.id to floatToString(it.amount)
                },
                isErrorAmountOfMaterial = materials.associate { it.materialModel.id to false },
                materialsForAdd = materialsForAdd,
            )
        }
    }
}
