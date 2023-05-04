package com.example.goodsaccounting.dashboard_manager.view.warehouse_history

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.theme.Black900
import com.example.goodsaccounting.common.view.theme.While200
import com.example.goodsaccounting.common.view.theme.padding

@Composable
internal fun TapWarehouseHistory(
    currentPage: Int,
    onClick: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .padding(vertical = MaterialTheme.padding.small2)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement
            .spacedBy(MaterialTheme.padding.medium2),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.padding.medium2
        )
    ) {
        itemsIndexed(listOf(R.string.sales, R.string.receipts, R.string.write_offs)){ index, stringRes ->
            val isSelected = currentPage == index
            val animatorColorBox by animateColorAsState(
                targetValue = if(isSelected) MaterialTheme.colors.primary
                else While200,
            )
            val animatorColorText by animateColorAsState(
                targetValue = if(isSelected) Color.White
                else Black900.copy(0.6f),
            )
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .alphaClick{
                        onClick(index)
                    }
                    .background(animatorColorBox)
                    .padding(
                        horizontal = MaterialTheme.padding.medium2,
                        vertical = MaterialTheme.padding.medium1
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = stringResource(stringRes),
                    style = MaterialTheme.typography.body2,
                    color = animatorColorText,
                    maxLines = 1,
                )
            }
        }
    }
}