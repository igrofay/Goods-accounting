package com.example.goodsaccounting.auth.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.goodsaccounting.R
import com.example.goodsaccounting.auth.model.AuthEvent
import com.example.goodsaccounting.auth.model.AuthState
import com.example.goodsaccounting.auth.model.AuthType
import com.example.goodsaccounting.common.view.edit_text.EditText
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.visual_transformation.PhonedVisualTransformation
import com.example.goodsaccounting.common.view_model.EventBase

@Composable
internal fun AuthInputFields(
    state: AuthState,
    eventBase: EventBase<AuthEvent>,
) {
    val verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.medium2)
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = verticalArrangement
    ) {
        AnimatedVisibility(
            visible = state.type == AuthType.SignUp
        ) {
            Column(
                verticalArrangement = verticalArrangement,
            ) {
                EditText(
                    value = state.firstname,
                    onChange = { eventBase.onEvent(AuthEvent.InputFirstname(it)) },
                    hint = stringResource(id = R.string.firstname),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    isError = state.isErrorFirstname,
                )
                EditText(
                    value = state.lastname,
                    onChange = { eventBase.onEvent(AuthEvent.InputLastname(it)) },
                    hint = stringResource(id = R.string.lastname),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    isError = state.isErrorLastname,
                )
                EditText(
                    value = state.patronymic,
                    onChange = { eventBase.onEvent(AuthEvent.InputPatronymic(it)) },
                    hint = stringResource(id = R.string.patronymic),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    isError = state.isErrorPatronymic,
                )
                EditText(
                    value = state.phone,
                    onChange = { eventBase.onEvent(AuthEvent.InputPhone(it)) },
                    hint = stringResource(id = R.string.phone),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = PhonedVisualTransformation(
                        PhonedVisualTransformation.russianMask,
                        PhonedVisualTransformation.numberCharForRussianMask,
                    ),
                    isError = state.isErrorPhone,
                )
            }
        }
        EditText(
            value = state.email,
            onChange = { eventBase.onEvent(AuthEvent.InputEmail(it)) },
            hint = stringResource(id = R.string.email),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = state.isErrorEmail,
        )
        EditText(
            value = state.password,
            onChange = { eventBase.onEvent(AuthEvent.InputPassword(it)) },
            hint = stringResource(id = R.string.password),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardActions = KeyboardActions {
                eventBase.onEvent(AuthEvent.Authorization)
            },
            isError = state.isErrorPassword,
        )
    }
}