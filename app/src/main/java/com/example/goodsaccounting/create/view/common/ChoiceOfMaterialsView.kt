package com.example.goodsaccounting.create.view.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.product.MaterialModel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.utils.getDesignation
import com.example.goodsaccounting.create.model.product.CreateProductState

@Composable
internal fun ChoiceOfMaterialsView(
    materials: Map<String, String>,
    materialsForAdd: Map<String, MaterialModel>,
    add: (id: String)-> Unit,
    remove: (id: String) -> Unit,
    back: ()-> Unit,
) {
    Column {
        Row(
            modifier = Modifier.padding(MaterialTheme.padding.medium2)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_keyboard_arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .alphaClick(onClick = back)
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = MaterialTheme.padding.medium2,
                vertical = MaterialTheme.padding.medium1
            )
        ){
            items(materialsForAdd.keys.toList()){ id->
                MaterialCard(
                    materialModel = materialsForAdd.getValue(id),
                    isSelected = materials.containsKey(id),
                    onSelectedChange = {isAdd->
                        if (isAdd){
                            add(id)
                        }else{
                            remove(id)
                        }
                    }
                )
            }
        }
    }

}

@Composable
private fun MaterialCard(
    materialModel: MaterialModel,
    isSelected: Boolean,
    onSelectedChange : (Boolean)-> Unit,
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
            Checkbox(
                checked = isSelected,
                onCheckedChange = onSelectedChange,
            )
        }
        Divider(
            startIndent = 100.dp + MaterialTheme.padding.medium1,
            modifier = Modifier.padding(vertical = MaterialTheme.padding.small2)
        )
    }
}