package com.example.goodsaccounting.create.view.product

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.create.model.product.CreateProductEvent
import com.example.goodsaccounting.create.model.product.CreateProductRouting
import com.example.goodsaccounting.create.model.product.CreateProductSideEffect
import com.example.goodsaccounting.create.view.common.ChoiceOfMaterialsView
import com.example.goodsaccounting.create.view_model.CreateProductVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun CreateProductScreen(
    exit: ()-> Unit,
) {
    val createProductVM by rememberDIAwareViewModel<CreateProductVM>()
    val state by createProductVM.collectAsState()
    createProductVM.collectSideEffect{sideEffect->
        when(sideEffect){
            CreateProductSideEffect.ProductCreated -> exit()
        }
    }
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = CreateProductRouting.FillingInFieldsWithInformation.route
    ) {
        composable(CreateProductRouting.FillingInFieldsWithInformation.route) {
            ProductInformationFields(
                state = state,
                eventBase = createProductVM,
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
                add ={id->
                    createProductVM.onEvent(CreateProductEvent.AddMaterial(id))
                },
                remove = {id->
                    createProductVM.onEvent(CreateProductEvent.RemoveMaterial(id))
                },
                back = {
                    navController.popBackStack()
                }
            )
        }
    }


}