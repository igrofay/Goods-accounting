package com.example.goodsaccounting.dashboard_manager.view.products_and_materials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.product.ProductModel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CustomTextButton
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.common.view.utils.getProductComponents
import com.example.goodsaccounting.common.view.utils.getCost

@Composable
internal fun ListProductView(
    listProduct: List<ProductModel>,
    edit: (idProduct: String)-> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(280.dp),
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.padding.medium2,
            vertical = MaterialTheme.padding.small2
        ),
        horizontalArrangement = Arrangement
            .spacedBy(MaterialTheme.padding.medium1),
        verticalArrangement = Arrangement
            .spacedBy(MaterialTheme.padding.medium1),
    ) {
        items(listProduct) { productModel ->
            ProductCard(productModel) {
                edit(productModel.id)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductCard(
    productModel: ProductModel,
    edit: ()-> Unit,
) {
    Card() {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            CustomImage(
                image = productModel.imageUrl,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
            )
            Divider()
            Column(
                modifier = Modifier.padding(
                   MaterialTheme.padding.medium1
                ),
            ) {
                Text(
                    text = productModel.name,
                    style = MaterialTheme.typography.h6,
                    maxLines = 3,
                )
                Text(
                    text = getCost(productModel.currency, productModel.price),
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                )
                val components = productModel.getProductComponents()
                if (components.isNotEmpty()){
                    Spacer(modifier = Modifier.height(MaterialTheme.padding.small2))
                    Text(
                        text = productModel.getProductComponents(),
                        style = MaterialTheme.typography.body2,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.textColor.copy(0.6f)
                    )
                }
                CustomTextButton(
                    label = stringResource(R.string.change),
                    color = MaterialTheme.colors.primary,
                ) {
                    edit()
                }
            }
        }
    }
}