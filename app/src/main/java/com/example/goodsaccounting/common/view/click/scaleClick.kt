package com.example.goodsaccounting.common.view.click

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput


private enum class ButtonScaleState{
    Idl, Pressed,
}

internal fun Modifier.scaleClick(enabled: Boolean= true,onClick: ()->Unit) = composed {
    var state by remember {
        mutableStateOf(ButtonScaleState.Idl)
    }
    val animScale by animateFloatAsState(
        targetValue = if(enabled) {
            when(state){
                ButtonScaleState.Idl -> 1f
                ButtonScaleState.Pressed -> 0.9f
            }
        }else{
            1f
        }
    )
    this
        .scale(animScale)
        .clickable(
            interactionSource = remember {
                MutableInteractionSource()
            },
            indication = null,
            enabled = enabled,
            onClick = onClick
        )
        .pointerInput(state){
            awaitPointerEventScope {
                state = when(state){
                    ButtonScaleState.Idl -> {
                        awaitFirstDown(false)
                        ButtonScaleState.Pressed
                    }
                    ButtonScaleState.Pressed -> {
                        waitForUpOrCancellation()
                        ButtonScaleState.Idl
                    }
                }
            }
        }
}