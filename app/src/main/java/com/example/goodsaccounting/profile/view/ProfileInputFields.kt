package com.example.goodsaccounting.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.core.domain.model.user.UserModel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.edit_text.EditText
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.visual_transformation.PhonedVisualTransformation
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.profile.model.ProfileEvent
import com.example.goodsaccounting.profile.model.ProfileState

@Composable
internal fun <State>  ProfileInputFields(
    state: State,
    eventBase: EventBase<ProfileEvent>
) where State : ProfileState, State : UserModel  {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.medium2)
    ){
        EditText(
            value = state.firstname,
            onChange = { eventBase.onEvent(ProfileEvent.InputFirstname(it)) },
            hint = stringResource(id = R.string.firstname),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
        isError = (state as? ProfileState.EditingUserData)?.isErrorFirstname ?: false
        )
        EditText(
            value = state.lastname,
            onChange = { eventBase.onEvent(ProfileEvent.InputLastname(it)) },
            hint = stringResource(id = R.string.lastname),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            isError = (state as? ProfileState.EditingUserData)?.isErrorLastname ?: false
        )
        EditText(
            value = state.patronymic,
            onChange = { eventBase.onEvent(ProfileEvent.InputPatronymic(it)) },
            hint = stringResource(id = R.string.patronymic),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            isError = (state as? ProfileState.EditingUserData)?.isErrorPatronymic ?: false,
        )
        EditText(
            value = state.phone,
            onChange = { eventBase.onEvent(ProfileEvent.InputPhone(it)) },
            hint = stringResource(id = R.string.phone),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            visualTransformation = PhonedVisualTransformation(
                PhonedVisualTransformation.russianMask,
                PhonedVisualTransformation.maskNumber,
            ),
            isError = (state as? ProfileState.EditingUserData)?.isErrorPhone ?: false
        )
        EditText(
            value = state.email,
            onChange = {  },
            readOnly = true,
            hint = stringResource(id = R.string.email),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
        )
    }
}
