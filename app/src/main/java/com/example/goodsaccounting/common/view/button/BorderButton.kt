package com.example.goodsaccounting.common.view.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.goodsaccounting.common.view.theme.padding

@Composable
internal fun BorderButton(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colorBorder: Color = MaterialTheme.colors.primary,
    textStyle: TextStyle = MaterialTheme.typography
        .button.copy(color = colorBorder),
    padding: PaddingValues = PaddingValues(
        vertical = MaterialTheme.padding.medium1
    ),
    onClick: ()-> Unit,
) {
    Box(
        modifier = modifier
            .scaleClick(enabled, onClick)
            .fillMaxWidth()
            .border(1.dp, colorBorder, MaterialTheme.shapes.medium)
            .padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = textStyle,
        )
    }
}