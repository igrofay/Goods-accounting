package com.example.goodsaccounting.create_or_edit.view.product

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.edit_text.EditText
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.common.view.utils.getDesignation
import com.example.goodsaccounting.common.view.visual_transformation.CurrencyAmountInputVisualTransformation
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductEvent
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductState

@Composable
internal fun MainFieldsView(
    state: CreateOrEditProductState.CreateOrEdit,
    eventBase: EventBase<CreateOrEditProductEvent>,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { uriIsNotNull ->
            eventBase.onEvent(
                CreateOrEditProductEvent.SelectImage(uriIsNotNull.toString())
            )
        }
    }
    CustomImage(
        image = state.imageUri,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable { launcher.launch("image/*") }
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.padding.extraLarge1)
            .padding(horizontal = MaterialTheme.padding.medium2),
        verticalArrangement = Arrangement
            .spacedBy(MaterialTheme.padding.medium2),
    ) {
        EditText(
            value = state.name,
            onChange = {
                eventBase.onEvent(CreateOrEditProductEvent.InputName(it))
            },
            hint = stringResource(R.string.product_name),
            isError = state.isErrorName
        )
        Card(
            border = if (state.isErrorPrice) BorderStroke(
                1.dp,
                MaterialTheme.colors.error.copy(0.6f)
            ) else null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.padding.medium1),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small2),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.product_price),
                    style = MaterialTheme.typography.subtitle1,
                )
                BasicTextField(
                    value = state.textPrice,
                    onValueChange = {
                        if (it.startsWith("0")) {
                            eventBase.onEvent(CreateOrEditProductEvent.InputPrice(""))
                        } else {
                            eventBase.onEvent(CreateOrEditProductEvent.InputPrice(it))
                        }

                    },
                    visualTransformation = CurrencyAmountInputVisualTransformation(
                        fixedCursorAtTheEnd = false,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Next,
                    ),
                    textStyle = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.textColor,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                var expanded by remember { mutableStateOf(false) }
                Box {
                    Row(
                        modifier = Modifier.alphaClick {
                            expanded = !expanded
                        },
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small1)
                    ) {
                        Text(
                            text = state.currency.getDesignation(),
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.textColor,
                        )
                        val animRotate by animateFloatAsState(
                            targetValue = if (expanded) 180f else 0f
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(animRotate),
                            tint = MaterialTheme.colors.onBackground,
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                    ) {
                        Currency.values().forEach { currency ->
                            DropdownMenuItem(onClick = {
                                eventBase.onEvent(
                                    CreateOrEditProductEvent.SelectCurrency(currency)
                                )
                                expanded = false
                            }) {
                                Text(
                                    text = currency.getDesignation(),
                                    style = MaterialTheme.typography.body2,
//                                    color = MaterialTheme.colors.textColor
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}