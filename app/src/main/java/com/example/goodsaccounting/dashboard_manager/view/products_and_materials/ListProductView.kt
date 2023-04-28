package com.example.goodsaccounting.dashboard_manager.view.products_and_materials

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.product.ProductModel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.utils.getProductComponents
import com.example.goodsaccounting.common.view.utils.getProductCost
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
internal fun ListProductView(
    listProduct: List<ProductModel>
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

            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductCard(
    productModel: ProductModel,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val color = MaterialTheme.colors.onBackground.copy(0.2f)
            var backgroundImage by remember { mutableStateOf(color) }
            GlideImage(
                imageModel = { productModel.imageUrl },
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .background(backgroundImage),
                failure = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_image),
                            contentDescription = null,
                        )
                    }
                },
                imageOptions = ImageOptions(
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillHeight
                ),
                success = { success ->
                    LaunchedEffect(success.imageBitmap) {
                        backgroundImage = Color(
                            success.imageBitmap!!
                                .asAndroidBitmap().getPixel(0, 0)
                        )
                    }
                    Image(
                        bitmap = success.imageBitmap!!,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
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
                    text = productModel.getProductCost(),
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                )
                val components = productModel.getProductComponents()
                if (components.isNotEmpty()){
                    Spacer(modifier = Modifier.height(MaterialTheme.padding.small2))
                    Text(
                        text = productModel.getProductComponents(),
                        style = MaterialTheme.typography.body1,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}