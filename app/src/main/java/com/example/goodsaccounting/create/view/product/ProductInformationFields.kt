package com.example.goodsaccounting.create.view.product

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CreateButton
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create.model.product.CreateProductEvent
import com.example.goodsaccounting.create.model.product.CreateProductState
import com.example.goodsaccounting.create.view.common.ListAmountOfMaterialView

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun ProductInformationFields(
    state: CreateProductState,
    eventBase: EventBase<CreateProductEvent>,
    choiceOfMaterials: ()-> Unit,
) {
    val scrollState = rememberScrollState()
    Column {
        Scaffold(
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
                        eventBase.onEvent(CreateProductEvent.InputAmountMaterial(amount, id))
                    }
                )
            }
        }
        CreateButton(
            isCreating = state.isCreating
        ){
            eventBase.onEvent(CreateProductEvent.Create)
        }
    }
}