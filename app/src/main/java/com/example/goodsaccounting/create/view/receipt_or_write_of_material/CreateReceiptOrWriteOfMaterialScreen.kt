package com.example.goodsaccounting.create.view.receipt_or_write_of_material

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.create.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialEvent
import com.example.goodsaccounting.create.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialRouting
import com.example.goodsaccounting.create.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialSideEffect
import com.example.goodsaccounting.create.view.common.ChoiceOfMaterialsView
import com.example.goodsaccounting.create.view_model.CreateReceiptOrWriteOfMaterialVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun CreateReceiptOrWriteOfMaterialScreen(
    created: ()->Unit,
) {
    val createReceiptOrWriteOfMaterialVM by rememberDIAwareViewModel<CreateReceiptOrWriteOfMaterialVM>()
    val state by createReceiptOrWriteOfMaterialVM.collectAsState()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val res = LocalContext.current.resources
    createReceiptOrWriteOfMaterialVM.collectSideEffect{ sideEffect ->
        when(sideEffect){
            is CreateReceiptOrWriteOfMaterialSideEffect.Message -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    res.getString(sideEffect.message)
                )
            }
            CreateReceiptOrWriteOfMaterialSideEffect.Created -> created()
        }
    }
    NavHost(navController = navController, startDestination = CreateReceiptOrWriteOfMaterialRouting.FillingInFieldsReceiptOrWriteOffMaterial.route){
        composable(CreateReceiptOrWriteOfMaterialRouting.FillingInFieldsReceiptOrWriteOffMaterial.route){
            CreateReceiptOrWriteOfMaterialFields(
                state = state,
                eventBase = createReceiptOrWriteOfMaterialVM,
                scaffoldState = scaffoldState,
                choiceOfMaterials = {
                    navController.navigate(CreateReceiptOrWriteOfMaterialRouting.ChoiceOfMaterials.route){
                        popUpTo(CreateReceiptOrWriteOfMaterialRouting.FillingInFieldsReceiptOrWriteOffMaterial.route)
                    }
                }
            )
        }
        composable(CreateReceiptOrWriteOfMaterialRouting.ChoiceOfMaterials.route){
            ChoiceOfMaterialsView(
                materials = state.materials,
                materialsForAdd = state.materialsForAddInList,
                add = {
                      createReceiptOrWriteOfMaterialVM.onEvent(
                          CreateReceiptOrWriteOfMaterialEvent.AddMaterial(it)
                      )
                },
                remove = {
                    createReceiptOrWriteOfMaterialVM.onEvent(
                        CreateReceiptOrWriteOfMaterialEvent.RemoveMaterial(it)
                    )
                },
                back = {
                    navController.popBackStack()
                }
            )
        }
    }
}