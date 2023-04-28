package com.example.goodsaccounting.dashboard_manager.view.dashbord

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.theme.padding

@Composable
internal fun AnimatedTopBarView(
    value: Boolean,
    onValueChange: (Boolean)->Unit,
    durationAnim: Int,
    label: String?,
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
    val animationPaddingLabel by animateDpAsState(
        targetValue = if(value) MaterialTheme.padding.medium2 else 0.dp,
        animationSpec = tween(
            durationMillis = durationAnim,
            easing = FastOutSlowInEasing
        )
    )
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement
            .spacedBy(MaterialTheme.padding.medium2),
//        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
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
        label?.let { notNulLabel->
            Text(
                text = notNulLabel,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(
                    start = animationPaddingLabel
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }

}

