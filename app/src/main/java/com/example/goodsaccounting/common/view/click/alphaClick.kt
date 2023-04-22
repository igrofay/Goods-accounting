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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput


private enum class ButtonAlphaState{
    Idl, Pressed,
}


fun Modifier.alphaClick(enabled: Boolean = true, alpha: Float =  0.7f,onClick: ()->Unit) =composed {
    var state by remember {
        mutableStateOf(ButtonAlphaState.Idl)
    }
    val animScale by animateFloatAsState(
        targetValue = if(enabled) {
            when(state){
                ButtonAlphaState.Idl -> 1f
                ButtonAlphaState.Pressed -> alpha
            }
        }else{
            1f
        }
    )
    this
        .alpha(animScale)
        .clickable(
            interactionSource = remember {
                MutableInteractionSource()
            },
            indication = null,
                    enabled = enabled,
            onClick = onClick
        )
        .pointerInput(state) {
            awaitPointerEventScope {
                state = when (state) {
                    ButtonAlphaState.Idl -> {
                        awaitFirstDown(false)
                        ButtonAlphaState.Pressed
                    }
                    ButtonAlphaState.Pressed -> {
                        waitForUpOrCancellation()
                        ButtonAlphaState.Idl
                    }
                }
            }
        }
}