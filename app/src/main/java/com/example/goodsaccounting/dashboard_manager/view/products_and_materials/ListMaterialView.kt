package com.example.goodsaccounting.dashboard_manager.view.products_and_materials

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.product.MaterialModel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CustomTextButton
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.common.view.utils.getDesignation
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
internal fun ListMaterialView(
    listMaterial: List<MaterialModel>,
    edit: (idMaterial: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),

        contentPadding = PaddingValues(
            horizontal = MaterialTheme.padding.medium2,
            vertical = MaterialTheme.padding.small2,
        )
    ) {
        items(listMaterial) { material ->
            MaterialCard(material){
                edit(material.id)
            }
        }
    }
}


@Composable
private fun MaterialCard(
    materialModel: MaterialModel,
    edit: ()-> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement
                .spacedBy(MaterialTheme.padding.medium1),
        ) {
            CustomImage(
                image = materialModel.imageUrl,
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
                    .shadow(1.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small1),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = materialModel.name,
                    maxLines = 1,
                    modifier = Modifier,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "${stringResource(id = R.string.measurement_type)}: ${materialModel.measurement.getDesignation()}",
                    modifier = Modifier,
                    maxLines = 1,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            CustomTextButton(
                label = stringResource(R.string.change),
                color = MaterialTheme.colors.primary,
                textStyle = MaterialTheme.typography.body2,
                onClick = edit
            )
        }
        Divider(
            startIndent = 100.dp + MaterialTheme.padding.medium1,
            modifier = Modifier.padding(vertical = MaterialTheme.padding.small2)
        )
    }

}
