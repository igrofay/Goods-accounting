package com.example.goodsaccounting.restore_password.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CreateButton
import com.example.goodsaccounting.common.view.edit_text.EditText
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.restore_password.model.RestorePasswordEvent
import com.example.goodsaccounting.restore_password.model.RestorePasswordState

@Composable
internal fun InputEmailView(
    state: RestorePasswordState,
    eventBase: EventBase<RestorePasswordEvent>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.password_recovery),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(horizontal = MaterialTheme.padding.medium2),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.padding.medium2))
        Text(
            text = stringResource(R.string.enter_your_email),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = MaterialTheme.padding.medium2),
        )
        Spacer(modifier = Modifier.weight(1f))
        EditText(
            value = state.email,
            onChange = {
                eventBase.onEvent(RestorePasswordEvent.InputEmail(it))
            },
            hint = stringResource(R.string.email),
            isError = state.isErrorEmail,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
            ),
            modifier = Modifier.padding(horizontal = MaterialTheme.padding.medium2),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.padding.medium1))
        CreateButton(
            isCreating = state.codeIsSending,
            label = stringResource(R.string.next),
        ) {
            eventBase.onEvent(RestorePasswordEvent.PushEmail)
        }
        Spacer(modifier = Modifier.weight(4f))
    }
}