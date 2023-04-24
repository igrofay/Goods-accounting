package com.example.goodsaccounting.dashboard_manager.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.alphaClick

@Composable
internal fun AnimatedMenuView(
    value: Boolean,
    onValueChange: (Boolean)->Unit,
    durationAnim: Int,
    modifier: Modifier,
) {
    val animationScaleMenu by animateFloatAsState(
        targetValue = if(value) 0f else 1f,
        animationSpec = if (value) tween(
            durationMillis = durationAnim/2
        ) else tween(
            durationMillis = durationAnim/2,
            delayMillis = durationAnim/2
        )
    )
    val animationScaleBack by animateFloatAsState(
        targetValue = if(value) 1f else 0f,
        animationSpec = if (value) tween(
            durationMillis = durationAnim/2,
            delayMillis = durationAnim/2
        ) else tween(
            durationMillis = durationAnim/2,
        )
    )
    Box(
        modifier = modifier
            .size(32.dp)
            .alphaClick { onValueChange(!value) }
    ){
        Icon(
            painter = painterResource(R.drawable.ic_hamburger_menu),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(animationScaleMenu)
        )
        Icon(
            painter = painterResource(R.drawable.ic_keyboard_arrow_left),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(animationScaleBack)
        )
    }
}