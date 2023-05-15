package com.example.goodsaccounting.create_or_edit.view.product

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductEvent
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductState
import com.example.goodsaccounting.create_or_edit.model.product.CreateProductRouting
import com.example.goodsaccounting.create_or_edit.view.common.ChoiceOfMaterialsView

@Composable
internal fun CreateOrEditView(
    state: CreateOrEditProductState.CreateOrEdit,
    eventBase: EventBase<CreateOrEditProductEvent>,
    scaffoldState: ScaffoldState,
) {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = CreateProductRouting.FillingInFieldsWithInformation.route
    ) {
        composable(CreateProductRouting.FillingInFieldsWithInformation.route) {
            ProductInformationFields(
                state = state,
                eventBase = eventBase,
                scaffoldState = scaffoldState,
                choiceOfMaterials = {
                    navController.navigate(CreateProductRouting.ChoiceOfMaterialsForProduct.route) {
                        popUpTo(CreateProductRouting.FillingInFieldsWithInformation.route)
                    }
                }
            )
        }
        composable(CreateProductRouting.ChoiceOfMaterialsForProduct.route) {
            ChoiceOfMaterialsView(
                materials = state.materials,
                materialsForAdd = state.materialsForAdd,
                add = { id ->
                    eventBase.onEvent(CreateOrEditProductEvent.AddMaterial(id))
                },
                remove = { id ->
                    eventBase.onEvent(CreateOrEditProductEvent.RemoveMaterial(id))
                },
                back = {
                    navController.popBackStack()
                }
            )
        }
    }
}