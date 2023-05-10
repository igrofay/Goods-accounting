package com.example.goodsaccounting.create_or_edit.view.receipt_or_write_of_material

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.edit_text.EditText
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create_or_edit.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialEvent
import com.example.goodsaccounting.create_or_edit.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialState
import com.example.goodsaccounting.create_or_edit.view.common.PhotoSelectionPagerView

@Composable
internal fun MainFieldsView(
    state: CreateReceiptOrWriteOfMaterialState,
    eventBase: EventBase<CreateReceiptOrWriteOfMaterialEvent>
) {
    PhotoSelectionPagerView(
        listImageUri = state.listImageUri,
        add = { position, uri ->
              eventBase.onEvent(CreateReceiptOrWriteOfMaterialEvent.SelectImage(uri, position))
        },
        remove = {position->
            eventBase.onEvent(CreateReceiptOrWriteOfMaterialEvent.RemoveImage(position))
        },
    )
    Spacer(modifier = Modifier.height(MaterialTheme.padding.medium2))
    EditText(
        value = state.name,
        onChange = {
            eventBase.onEvent(CreateReceiptOrWriteOfMaterialEvent.InputName(it))
        },
        modifier = Modifier.padding(
            horizontal = MaterialTheme.padding.medium2,
        ),
        hint = if (state.isReceipt)
            stringResource(R.string.name_of_receipt)
        else stringResource(R.string.name_of_write_off),
        isError = state.isErrorName,
    )
}