package com.example.goodsaccounting.common.view.edit_text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.common.view.theme.Padding
import com.example.goodsaccounting.common.view.theme.While200
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor

@Composable
internal fun EditText(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    isError: Boolean = false,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography
        .body1.copy(color = Color.Black),
    shape: Shape = MaterialTheme.shapes.medium,
    padding: PaddingValues = PaddingValues(
        MaterialTheme.padding.medium1
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    BasicTextField(
        value = value,
        onValueChange = onChange,
        textStyle = if (isError) textStyle.copy(color = MaterialTheme.colors.error)
        else textStyle,
        singleLine = true,
        readOnly = readOnly,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    ) { innerTextField ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape)
                .background(
                    While200,
                    shape,
                )
                .border(
                    1.5.dp,
                    if (isError) MaterialTheme.colors.error.copy(0.6f)
                    else MaterialTheme.colors.textColor.copy(0.2f),
                    shape
                )
                .padding(
                    padding
                ),
        ) {
            if (hint != null && value.isBlank()) {
                Text(
                    text = hint,
                    style = textStyle.copy(
                        color = (if (isError) MaterialTheme.colors.error
                        else Color.Black).copy(0.5f)
                    )
                )
            }
            innerTextField()
        }
    }
}