package com.example.goodsaccounting.create_or_edit.view.sale_seller

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.product.Currency
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CreateButton
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.utils.getCost
import com.example.goodsaccounting.common.view.utils.getDesignation
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create_or_edit.model.common.CreateOrEditState
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerEvent
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerState
import com.example.goodsaccounting.create_or_edit.view.common.ListAmountOfProductView

@Composable
@OptIn(ExperimentalAnimationApi::class)
internal fun CreateSaleSellerFields(
    state: CreateOrEditSaleSellerState.CreateOrEdit,
    eventBase: EventBase<CreateOrEditSaleSellerEvent>,
    scaffoldState: ScaffoldState,
    choiceOfProducts: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.adding_sale),
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(vertical = MaterialTheme.padding.medium2)
        )
        Scaffold(
            modifier = Modifier.weight(1f),
            scaffoldState = scaffoldState,
            floatingActionButton = {
                AnimatedVisibility(
                    visible = scrollState.canScrollForward || !scrollState.canScrollBackward,
                    exit = fadeOut() + scaleOut(),
                    enter = fadeIn() + scaleIn()
                ) {
                    FloatingActionButton(
                        onClick = choiceOfProducts
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                        )
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
                MainFieldView(
                    state = state,
                    eventBase = eventBase,
                )
                ListAmountOfProductView(
                    products = state.products,
                    listProductForAdd = state.listProductForAdd,
                    isErrorAmountOfProduct = state.isErrorAmountOfProduct,
                    onAmountChange = { id, amount ->
                        eventBase.onEvent(CreateOrEditSaleSellerEvent.InputAmountOfProduct(id,amount))
                    }
                )
            }
        }
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.padding.medium2,
                    vertical = MaterialTheme.padding.medium1
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.sum),
                style = MaterialTheme.typography.h6,
            )
            Box{
                var expanded by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier.alphaClick {
                        expanded = !expanded
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        MaterialTheme.padding.small2
                    )
                ){
                    Text(
                        text = getCost(state.currency, state.checkPrice ),
                        style = MaterialTheme.typography.subtitle1,
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
                                CreateOrEditSaleSellerEvent.SelectCurrency(currency)
                            )
                            expanded = false
                        }) {
                            Text(
                                text = currency.getDesignation(),
                                style = MaterialTheme.typography.body2,
                            )
                        }
                    }
                }
            }
        }
        CreateButton(
            isCreating = state.isCreatingOrEditing,
            label =  when (state.createOrEditState){
                CreateOrEditState.Create -> stringResource(R.string.create)
                CreateOrEditState.Edit -> stringResource(R.string.edit)
            }
        ) {
           when (state.createOrEditState){
               CreateOrEditState.Create ->  eventBase.onEvent(CreateOrEditSaleSellerEvent.CreateOrEdit)
               CreateOrEditState.Edit ->  eventBase.onEvent(CreateOrEditSaleSellerEvent.Edit)
           }
        }
    }
}