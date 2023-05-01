package com.example.goodsaccounting.create.view.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CustomTextButton
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create.model.product.CreateProductEvent
import com.example.goodsaccounting.create.model.product.CreateProductState

@Composable
internal fun MaterialsView(
    state: CreateProductState,
    eventBase: EventBase<CreateProductEvent>
) {
    var isDisplayDialogExplainingMaterials by remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small2),
        modifier = Modifier.padding(MaterialTheme.padding.medium2)
    ) {
        Text(
            text = stringResource(R.string.materials),
            style = MaterialTheme.typography.h6
        )
        Icon(
            painter = painterResource(R.drawable.ic_question_mark),
            contentDescription = null,
            modifier = Modifier
                .alphaClick { isDisplayDialogExplainingMaterials = true }
                .size(20.dp)
                .border(
                    1.dp,
                    MaterialTheme.colors.onSurface.copy(0.7f),
                    CircleShape,
                )
                .padding(MaterialTheme.padding.small1)
        )
    }
    Column(
        Modifier
    ) {

    }
    if (isDisplayDialogExplainingMaterials){
        Dialog(onDismissRequest = { isDisplayDialogExplainingMaterials = false }) {
            Column(
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.background,
                        MaterialTheme.shapes.medium
                    )
                    .padding(MaterialTheme.padding.medium2),
                verticalArrangement = Arrangement
                    .spacedBy(MaterialTheme.padding.small2)
            ) {
                Text(
                    text = stringResource(R.string.materials_used_in_manufacture_of_product),
                    style = MaterialTheme.typography.subtitle1,
                )
                CustomTextButton(
                    label = stringResource(id = R.string.ok),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    isDisplayDialogExplainingMaterials = false
                }
            }
        }
    }
}