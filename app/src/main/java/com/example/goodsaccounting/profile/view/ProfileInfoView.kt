package com.example.goodsaccounting.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core.domain.model.user.UserModel
import com.example.goodsaccounting.common.view.button.CustomButton
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.profile.model.ProfileEvent
import com.example.goodsaccounting.profile.model.ProfileState
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.BorderButton

@Composable
internal fun <State> ProfileInfoView(
    state: State,
    eventBase: EventBase<ProfileEvent>,
) where State : ProfileState, State : UserModel {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = MaterialTheme.padding.medium2,
                vertical = MaterialTheme.padding.small2
            ),
    ) {
        HeaderView(state.imageUrl, state.role, eventBase)
        Spacer(modifier = Modifier.height(MaterialTheme.padding.medium2))
        ProfileInputFields(state, eventBase)
        Spacer(modifier = Modifier.height(MaterialTheme.padding.medium2))
        if ((state as? ProfileState.EditingUserData)?.isUpdating == true) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = MaterialTheme.padding.medium2)
            )
        } else {
            CustomButton(
                label = stringResource(id = R.string.save),
                enabled = state is ProfileState.EditingUserData
            ) {
                eventBase.onEvent(ProfileEvent.Save)
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.padding.medium2))
        BorderButton(
            label = stringResource(R.string.exit),
            colorBorder = MaterialTheme.colors.error
        ) {
            eventBase.onEvent(ProfileEvent.Exit)
        }
        Spacer(modifier = Modifier.height(MaterialTheme.padding.small2))
    }
}