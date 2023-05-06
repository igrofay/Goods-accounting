package com.example.goodsaccounting.create.view.sale_seller

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
import com.example.goodsaccounting.create.model.sale_seller.CreateSaleSellerEvent
import com.example.goodsaccounting.create.model.sale_seller.CreateSaleSellerState
import com.example.goodsaccounting.create.view.common.PhotoSelectionPagerView

@Composable
internal fun MainFieldView(
    state: CreateSaleSellerState,
    eventBase: EventBase<CreateSaleSellerEvent>
) {
    PhotoSelectionPagerView(
        listImageUri =state.listImageUri ,
        add = { position, uri ->
            eventBase.onEvent(CreateSaleSellerEvent.SelectImage(position, uri))
        } ,
        remove = {position ->
            eventBase.onEvent(CreateSaleSellerEvent.RemoveImage(position))
        },
    )
    Spacer(modifier = Modifier.height(MaterialTheme.padding.medium2))
    EditText(
        value = state.name,
        onChange = {
            eventBase.onEvent(CreateSaleSellerEvent.InputName(it))
        },
        modifier = Modifier.padding(
            horizontal = MaterialTheme.padding.medium2,
        ),
        hint = stringResource(R.string.sale_name),
        isError = state.isErrorName,
    )
}