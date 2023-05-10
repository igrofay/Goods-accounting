package com.example.goodsaccounting.create_or_edit.model.product

import com.example.goodsaccounting.nav.model.AppRouting

internal sealed class CreateProductRouting(route: String) : AppRouting(route){
    object FillingInFieldsWithInformation : CreateProductRouting("filling_in_fields_with_information")
    object ChoiceOfMaterialsForProduct : CreateProductRouting("choice_of_materials_for_product")
}