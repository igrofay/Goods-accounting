package com.example.goodsaccounting.sales_seller.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.sale.SaleModel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CustomTextButton
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.common.view.utils.getMaterialsName
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.sales_seller.model.SalesSellerEvent
import com.example.goodsaccounting.sales_seller.model.SalesSellerState

@Composable
internal fun ListSaleSellerView(
    state: SalesSellerState,
    eventBase: EventBase<SalesSellerEvent>,
    editSale: (idSale: String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(344.dp),
    ){
        items(state.listSale){sale->
            SaleCard(
                sale = sale,
                edit = {

                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SaleCard(
    sale: SaleModel,
    edit: ()-> Unit,
) {
    Card {
        Column {
            HorizontalPager(
                pageCount = sale.imagesUrl.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp)
            ) {
                CustomImage(
                    image = sale.imagesUrl[it],
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.padding.small2))
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.padding.medium1,
                    )
            ) {
                Text(
                    text = sale.name,
                    maxLines = 2,
                    style = MaterialTheme.typography.subtitle1,
                )
                Text(
                    text = "${sale.creatorName} ${sale.date}",
                    maxLines = 1,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.textColor.copy(0.6f)
                )
                Text(
                    text = sale.getMaterialsName(),
                    maxLines = 3,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.textColor.copy(0.6f)
                )
                Row {
                    CustomTextButton(
                        label = stringResource(R.string.edit),
                        onClick = edit,
                        color = MaterialTheme.colors.primary,
                    )
                }
            }
        }
    }
}