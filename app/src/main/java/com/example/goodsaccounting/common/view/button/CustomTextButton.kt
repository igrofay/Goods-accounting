package com.example.goodsaccounting.common.view.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.theme.padding

@Composable
internal fun CustomTextButton(
    label:String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    color: Color = MaterialTheme.colors.secondary,
    contentPadding: PaddingValues = PaddingValues(
        vertical = MaterialTheme.padding.small2
    ),
    onClick: ()-> Unit,
) {
    Text(
        text = label,
        style = textStyle.copy(color = color),
        modifier = modifier
            .alphaClick(onClick = onClick)
            .padding(contentPadding)
    )
}