package com.example.goodsaccounting.common.view.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.theme.padding

@Composable
internal fun CreateButton(
    isCreating: Boolean,
    label: String = stringResource(R.string.create),
    create: ()-> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ){
        if (isCreating) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.padding.medium2)
            )
        } else {
            CustomButton(
                label = label,
                modifier = Modifier.padding(MaterialTheme.padding.medium1),
                onClick = create
            )
        }
    }
}