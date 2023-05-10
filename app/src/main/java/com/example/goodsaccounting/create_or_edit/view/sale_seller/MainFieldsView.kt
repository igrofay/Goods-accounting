package com.example.goodsaccounting.create_or_edit.view.sale_seller

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
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerEvent
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerState
import com.example.goodsaccounting.create_or_edit.view.common.PhotoSelectionPagerView

@Composable
internal fun MainFieldView(
    state: CreateOrEditSaleSellerState.CreateOrEdit,
    eventBase: EventBase<CreateOrEditSaleSellerEvent>
) {
    PhotoSelectionPagerView(
        listImageUri =state.listImageUri ,
        add = { position, uri ->
            eventBase.onEvent(CreateOrEditSaleSellerEvent.SelectImage(position, uri))
        } ,
        remove = {position ->
            eventBase.onEvent(CreateOrEditSaleSellerEvent.RemoveImage(position))
        },
    )
    Spacer(modifier = Modifier.height(MaterialTheme.padding.medium2))
    EditText(
        value = state.name,
        onChange = {
            eventBase.onEvent(CreateOrEditSaleSellerEvent.InputName(it))
        },
        modifier = Modifier.padding(
            horizontal = MaterialTheme.padding.medium2,
        ),
        hint = stringResource(R.string.sale_name),
        isError = state.isErrorName,
    )
}