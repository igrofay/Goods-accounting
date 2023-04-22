package com.example.goodsaccounting.common.view.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.goodsaccounting.common.view.theme.padding

@Composable
internal fun CustomButton(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colorBackground: Color = MaterialTheme.colors.primary,
    padding: PaddingValues = PaddingValues(
        vertical = MaterialTheme.padding.medium1
    ),
    textStyle: TextStyle = MaterialTheme.typography
        .button.copy(color = Color.White),
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .scaleClick(enabled, onClick)
            .alpha(if (enabled) 1f else 0.6f )
            .fillMaxWidth()
            .background(
                colorBackground,
                MaterialTheme.shapes.medium
            )
            .padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = textStyle,
        )
    }
}