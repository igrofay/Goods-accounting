package com.example.goodsaccounting.dashboard_manager.view.warehouse_history

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.sale.SaleModel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.common.view.utils.getAmountOfProductName
import com.example.goodsaccounting.common.view.utils.getCost
import com.example.goodsaccounting.common.view.utils.getDateString

@Composable
internal fun ListSaleView(
    listSale : List<SaleModel>,
) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.padding.medium2,
            vertical = MaterialTheme.padding.medium1
        ),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement
            .spacedBy(MaterialTheme.padding.medium1),
        horizontalArrangement = Arrangement
            .spacedBy(MaterialTheme.padding.medium1),
        columns = GridCells.Adaptive(280.dp)
    ){
        items(listSale){ sale->
            SaleCard(sale)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SaleCard(
    sale: SaleModel
) {
    Card {
        Column {
            val pagerState = rememberPagerState()
            HorizontalPager(
                pageCount = sale.imagesUrl.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                state = pagerState,
            ) {
                CustomImage(
                    image = sale.imagesUrl[it],
                    modifier = Modifier.fillMaxSize()
                )
            }
            if (sale.imagesUrl.size > 1){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement
                        .spacedBy(MaterialTheme.padding.small1, Alignment.CenterHorizontally),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.padding.small2 * 1.5f),
                ){
                    for(index in sale.imagesUrl.indices){
                        val animationColor by animateColorAsState(
                            if (pagerState.currentPage == index) MaterialTheme.colors.secondary
                            else MaterialTheme.colors.onSurface.copy(0.3f)
                        )
                        Spacer(modifier = Modifier
                            .size(8.dp)
                            .background(animationColor, CircleShape))
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.padding.medium1,
                        vertical = MaterialTheme.padding.small2 * 1.5f,
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ){
                    Text(
                        text = sale.name,
                        maxLines = 2,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.weight(3f),
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = getCost(currencyData = sale.currency, cost = sale.checkPrice),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.weight(2f),
                        textAlign = TextAlign.End,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = sale.creatorName,
                        maxLines = 1,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.textColor.copy(0.6f),
                        modifier = Modifier.weight(3f)
                    )
                    Text(
                        text = getDateString(sale.date),
                        maxLines = 1,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.textColor.copy(0.6f),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.padding.small2))
                Text(
                    text = stringResource(R.string.sold) +": "+ sale.getAmountOfProductName(),
                    maxLines = 3,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.textColor.copy(0.6f)
                )
            }
        }
    }
}