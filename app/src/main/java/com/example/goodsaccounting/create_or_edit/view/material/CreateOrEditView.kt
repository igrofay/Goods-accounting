package com.example.goodsaccounting.create_or_edit.view.material

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CreateButton
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create_or_edit.model.common.CreateOrEditState
import com.example.goodsaccounting.create_or_edit.model.material.CreateOrEditMaterialEvent
import com.example.goodsaccounting.create_or_edit.model.material.CreateOrEditMaterialState

@Composable
internal fun CreateOrEditView(
    state: CreateOrEditMaterialState.CreateOrEdit,
    eventBase: EventBase<CreateOrEditMaterialEvent>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MainSettingView(
            state = state,
            eventBase = eventBase,
        )
        Spacer(modifier = Modifier.weight(1f))
        CreateButton(
            isCreating = state.isCreatingOrEdit,
            label = when(state.createOrEditState){
                CreateOrEditState.Create -> stringResource(R.string.create)
                CreateOrEditState.Edit -> stringResource(R.string.edit)
            }
        ){
            eventBase.onEvent(CreateOrEditMaterialEvent.CreateOrEdit)
        }
    }
}