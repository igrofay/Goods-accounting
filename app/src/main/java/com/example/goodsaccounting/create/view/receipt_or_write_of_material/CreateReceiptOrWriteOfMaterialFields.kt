package com.example.goodsaccounting.create.view.receipt_or_write_of_material

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CreateButton
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialEvent
import com.example.goodsaccounting.create.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialState
import com.example.goodsaccounting.create.view.common.ListAmountOfMaterialView

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun CreateReceiptOrWriteOfMaterialFields(
    state: CreateReceiptOrWriteOfMaterialState,
    eventBase: EventBase<CreateReceiptOrWriteOfMaterialEvent>,
    scaffoldState: ScaffoldState,
    choiceOfMaterials: ()-> Unit,
) {
    Column {
        Text(
            text = if (state.isReceipt) stringResource(R.string.receipt_of_material)
            else stringResource(R.string.write_off_material),
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = MaterialTheme.padding.medium2)
        )
        val scrollState = rememberScrollState()
        Scaffold(
            modifier = Modifier.weight(1f),
            scaffoldState = scaffoldState,
            floatingActionButton = {
                AnimatedVisibility(
                    visible = scrollState.canScrollForward || !scrollState.canScrollBackward,
                    exit = fadeOut() + scaleOut(),
                    enter =  fadeIn() + scaleIn(),
                ) {
                    FloatingActionButton(onClick = choiceOfMaterials) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                MainFieldsView(
                    state = state,
                    eventBase = eventBase,
                )
                ListAmountOfMaterialView(
                    dialogMessage = if (state.isReceipt) stringResource(R.string.materials_that_arrived_at_warehouse)
                    else stringResource(R.string.materials_that_are_written_off_from_warehouse),
                    listMaterialForAdd = state.materialsForAddInList,
                    materials = state.materials,
                    isErrorAmountOfMaterial = state.isErrorAmountOfMaterial,
                    onAmountChange = {id, amount ->
                        eventBase.onEvent(CreateReceiptOrWriteOfMaterialEvent.InputAmountMaterial(id, amount))
                    }
                )
            }
        }
        CreateButton(isCreating = state.isCreating) {
            eventBase
                .onEvent(CreateReceiptOrWriteOfMaterialEvent.Create)
        }
    }
}