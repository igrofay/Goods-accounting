package com.example.goodsaccounting.create_or_edit.view.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CreateButton
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create_or_edit.model.common.CreateOrEditState
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductEvent
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductState
import com.example.goodsaccounting.create_or_edit.view.common.ListAmountOfMaterialView

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ProductInformationFields(
    state: CreateOrEditProductState.CreateOrEdit,
    eventBase: EventBase<CreateOrEditProductEvent>,
    scaffoldState: ScaffoldState,
    choiceOfMaterials: ()-> Unit,
) {
    val scrollState = rememberScrollState()
    Column {
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButton = {
                AnimatedVisibility(
                    visible = scrollState.canScrollForward || !scrollState.canScrollBackward,
                    exit = fadeOut() + scaleOut(),
                    enter =  fadeIn() + scaleIn()
                ) {
                    FloatingActionButton(
                        onClick = choiceOfMaterials
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                        )
                    }
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(scrollState)
                    .weight(1f)
            ) {
                MainFieldsView(
                    state = state,
                    eventBase = eventBase,
                )
                ListAmountOfMaterialView(
                    dialogMessage = stringResource(R.string.materials_used_in_manufacture_of_product),
                    listMaterialForAdd = state.materialsForAdd,
                    materials = state.materials,
                    isErrorAmountOfMaterial = state.isErrorAmountOfMaterial,
                    onAmountChange = { id, amount ->
                        eventBase.onEvent(CreateOrEditProductEvent.InputAmountMaterial(amount, id))
                    }
                )
            }
        }
        CreateButton(
            isCreating = state.isCreatingOrEditing,
            label = when(state.createOrEditState){
                CreateOrEditState.Create -> stringResource(R.string.create)
                CreateOrEditState.Edit -> stringResource(R.string.edit)
            }
        ){
            eventBase.onEvent(CreateOrEditProductEvent.CreateOrEdit)
        }
    }
}