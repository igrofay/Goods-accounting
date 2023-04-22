package com.example.goodsaccounting.auth.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.auth.model.AuthEvent
import com.example.goodsaccounting.auth.model.AuthState
import com.example.goodsaccounting.auth.model.AuthType
import com.example.goodsaccounting.common.view.button.BorderButton
import com.example.goodsaccounting.common.view.button.CustomButton
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.EventBase

@Composable
internal fun ActionButtons(state: AuthState, eventBase: EventBase<AuthEvent>) {
    Column(
        modifier = Modifier
            .padding(vertical = MaterialTheme.padding.medium1),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.medium1)
    ) {
        CustomButton(
            label = when (state.type) {
                AuthType.SignIn -> stringResource(id = R.string.i_dont_have_account)
                AuthType.SignUp -> stringResource(id = R.string.i_have_account)
            },
        ) {
            when (state.type) {
                AuthType.SignIn -> eventBase.onEvent(AuthEvent.SetSingUp)
                AuthType.SignUp -> eventBase.onEvent(AuthEvent.SetSignIn)
            }
        }
        BorderButton(
            label = stringResource(id = R.string.forgot_password),
            colorBorder = MaterialTheme.colors.secondary
        ){
            eventBase.onEvent(AuthEvent.RestorePassword)
        }
    }
}