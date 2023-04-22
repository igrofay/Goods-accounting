package com.example.goodsaccounting.common.view.button

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.goodsaccounting.common.view.click.alphaClick

@Composable
internal fun CustomTextButton(
    label:String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
    onClick: ()-> Unit,
) {
    Text(
        text = label,
        style = textStyle,
        modifier = modifier.alphaClick(onClick = onClick)
    )
}