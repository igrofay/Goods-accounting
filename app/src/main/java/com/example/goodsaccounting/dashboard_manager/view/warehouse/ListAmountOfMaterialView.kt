package com.example.goodsaccounting.dashboard_manager.view.warehouse

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.product.AmountOfMaterialModel
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.common.view.utils.getDesignation
import com.example.goodsaccounting.common.view.utils.transformationToString

@Composable
internal fun ListAmountOfMaterialView(
    list: List<AmountOfMaterialModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.padding.medium2,
            vertical = MaterialTheme.padding.medium1
        ),
    ){
        items(list){
            AmountOfMaterialCard(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AmountOfMaterialCard(
    amountOfMaterialModel: AmountOfMaterialModel,
) {
    val color = if(
        amountOfMaterialModel.amount > amountOfMaterialModel.materialModel.minimumQuantity
    ) MaterialTheme.colors.textColor
    else MaterialTheme.colors.error
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement
                .spacedBy(MaterialTheme.padding.medium1),
        ) {
            CustomImage(
                image = amountOfMaterialModel.materialModel.imageUrl,
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
                    .shadow(1.dp)
            )
            Text(
                text = amountOfMaterialModel.materialModel.name,
                maxLines = 1,
                modifier = Modifier.weight(2f),
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${amountOfMaterialModel.amount.transformationToString()} ${amountOfMaterialModel.materialModel.measurement.getDesignation()}" ,
                color = color,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1.5f)
                    .basicMarquee(),
                textAlign = TextAlign.End,
                maxLines = 1,
            )
        }
        Divider(
            startIndent = 100.dp + MaterialTheme.padding.medium1,
            modifier = Modifier.padding(vertical = MaterialTheme.padding.small2)
        )
    }
}