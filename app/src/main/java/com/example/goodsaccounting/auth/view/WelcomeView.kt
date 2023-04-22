package com.example.goodsaccounting.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.theme.padding

@Composable
internal fun WelcomeView() {
    Column {
        Row(
            horizontalArrangement = Arrangement
                .spacedBy(MaterialTheme.padding.medium1),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.im_hello),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.h5,
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.padding.medium2))
        Text(
            text = stringResource(id = R.string.sign_in_to_use_app_features),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.padding.extraLarge1))
    }
}