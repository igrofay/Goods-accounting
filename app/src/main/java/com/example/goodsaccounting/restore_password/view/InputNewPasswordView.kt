package com.example.goodsaccounting.restore_password.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CreateButton
import com.example.goodsaccounting.common.view.edit_text.EditText
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.restore_password.model.RestorePasswordEvent
import com.example.goodsaccounting.restore_password.model.RestorePasswordState


@Composable
internal fun InputNewPasswordView(
    state: RestorePasswordState,
    eventBase: EventBase<RestorePasswordEvent>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.creating_new_password),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(horizontal = MaterialTheme.padding.medium2),
        )
        Spacer(modifier = Modifier.height(MaterialTheme.padding.medium2))
        Text(
            text = stringResource(R.string.enter_new_password),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = MaterialTheme.padding.medium2),
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.padding.medium1
            )
        ) {
            EditText(
                value = state.newPassword,
                onChange = {
                    eventBase.onEvent(RestorePasswordEvent.InputNewPassword(it))
                },
                hint = stringResource(R.string.password),
                isError = state.isErrorNewPassword,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.padding.medium2),
            )
            EditText(
                value = state.newPasswordReplay,
                onChange = {
                    eventBase.onEvent(RestorePasswordEvent.InputNewPasswordReplay(it))
                },
                hint = stringResource(R.string.password_replay),
                isError = state.isErrorNewPasswordReplay,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.padding.medium2),
            )
            CreateButton(
                isCreating = state.isResettingPassword,
                label = stringResource(R.string.update),
            ) {
                eventBase.onEvent(RestorePasswordEvent.SetNewPassword)
            }
        }
        Spacer(modifier = Modifier.weight(4f))
    }
}