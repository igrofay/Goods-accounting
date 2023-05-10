package com.example.goodsaccounting.create_or_edit.view.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.product.ProductModel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.common.view.utils.getCost

@Composable
internal fun ListAmountOfProductView(
    products: Map<String, Int?>,
    listProductForAdd: Map<String, ProductModel>,
    isErrorAmountOfProduct: Map<String, Boolean>,
    onAmountChange: (id: String, amount: String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small2),
        modifier = Modifier.padding(MaterialTheme.padding.medium2)
    ) {
        Text(
            text = stringResource(R.string.products),
            style = MaterialTheme.typography.h6
        )
//        Icon(
//            painter = painterResource(R.drawable.ic_question_mark),
//            contentDescription = null,
//            modifier = Modifier
//                .alphaClick { isDisplayDialogExplainingMaterials = true }
//                .size(20.dp)
//                .border(
//                    1.dp,
//                    MaterialTheme.colors.onSurface.copy(0.7f),
//                    CircleShape,
//                )
//                .padding(MaterialTheme.padding.small1)
//        )
    }
    Column(
        modifier = Modifier.padding(
            horizontal = MaterialTheme.padding.medium2,
        )
    ) {
        for (id in products.keys){
            AmountOfProductCard(
                product = listProductForAdd.getValue(id),
                amount = products[id],
                onAmountChange = {
                    onAmountChange(id, it)
                },
                isErrorAmount = isErrorAmountOfProduct.getValue(id)
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.padding.extraLarge1))
    }
}

@Composable
private fun AmountOfProductCard(
    product: ProductModel,
    amount: Int?,
    isErrorAmount: Boolean,
    onAmountChange: (String) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement
                .spacedBy(MaterialTheme.padding.medium1),
        ) {
            CustomImage(
                image = product.imageUrl,
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
                    text = product.name,
                    maxLines = 2,
                    modifier = Modifier,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "${stringResource(id = R.string.price)}: ${getCost(product.currency, product.price)}",
                    modifier = Modifier,
                    maxLines = 1,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            BasicTextField(
                value = amount?.toString() ?: "",
                onValueChange = onAmountChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next,
                ),
                textStyle = MaterialTheme.typography.body2.copy(
                    color = if(isErrorAmount)
                        MaterialTheme.colors.error else
                        MaterialTheme.colors.textColor ,
                    textAlign = TextAlign.End
                ),
                modifier = Modifier.weight(0.5f),
                singleLine = true
            ){innerTextField->
                Column(
                    verticalArrangement = Arrangement
                        .spacedBy(MaterialTheme.padding.small1),
                    horizontalAlignment = Alignment.End
                ){
                    innerTextField()
                    Divider(
                        color = if (isErrorAmount) MaterialTheme.colors.error.copy(alpha = 0.12f)
                        else MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                    )
                }
            }
        }
        Divider(
            startIndent = 100.dp + MaterialTheme.padding.medium1,
            modifier = Modifier.padding(vertical = MaterialTheme.padding.small2)
        )
    }
}