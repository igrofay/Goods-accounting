@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.goodsaccounting.restore_password.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.restore_password.model.RestorePasswordEvent
import com.example.goodsaccounting.restore_password.model.RestorePasswordState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

@Composable
internal fun InputCodeView(
    state: RestorePasswordState,
    eventBase: EventBase<RestorePasswordEvent>,
    back: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.padding.medium2)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_keyboard_arrow_left),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .alphaClick(onClick = back)
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.padding.medium2
            )
        ) {
            Text(
                text = stringResource(R.string.enter_code_from_email),
                style = MaterialTheme.typography.subtitle1
            )
            CodeFieldView(
                code = state.code,
                onCodeChange = { code ->
                    eventBase.onEvent(RestorePasswordEvent.InputCode(code))
                },
                isErrorInput = state.isErrorCode
            )
            var triggerTimer by remember {
                mutableStateOf(false)
            }
            val timer by remember(triggerTimer) {
                flow {
                    for (sec in (0..60).reversed()) {
                        emit(sec)
                        delay(1_000L)
                    }
                }
            }.collectAsState(initial = 60)
            Box(modifier = Modifier.heightIn(min = 40.dp)) {
                if (timer == 0) {
                    Text(
                        text = stringResource(R.string.send_code_again),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.textColor.copy(0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .alphaClick {
                                eventBase.onEvent(RestorePasswordEvent.SendCodeAgain)
                                triggerTimer = !triggerTimer
                            },
                        textDecoration = TextDecoration.Underline,
                    )
                } else {
                    Text(
                        text = stringResource(
                            R.string.you_can_send_the_code_again_after_n_seconds,
                            timer
                        ),
                        color = MaterialTheme.colors.textColor.copy(0.7f),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.weight(2f))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CodeFieldView(
    code: String,
    onCodeChange: (String) -> Unit,
    isErrorInput: Boolean,
    size: Int = 6,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    Box(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {
                focusRequester.requestFocus()
                keyboardController?.show()
            },
        )
    ) {
        BasicTextField(
            value = code,
            onValueChange = onCodeChange,
            modifier = Modifier
                .size(1.dp)
                .alpha(0f)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.None,
            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.padding.medium1
            ),
        ) {
            for (item in 0 until size) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(MaterialTheme.colors.surface, MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = code.getOrNull(item)?.toString() ?: "",
                        style = MaterialTheme.typography.subtitle1,
                        color = if (isErrorInput)
                            MaterialTheme.colors.error
                        else MaterialTheme.colors.textColor
                    )
                }
            }
        }
    }

}