package com.example.goodsaccounting.splash.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.min
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.splash.model.SplashSideEffect
import com.example.goodsaccounting.splash.view_model.SplashVM
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun SplashScreen(
    needAuth : ()-> Unit,
    displayUserContent: (RoleLevel)-> Unit,
) {
    val stateScaffold = rememberScaffoldState()
    val context = LocalContext.current
    val splashVM by rememberDIAwareViewModel<SplashVM>()

    splashVM.collectSideEffect { sideEffect ->
        when(sideEffect){
            SplashSideEffect.GoToAuthentication -> needAuth()
            is SplashSideEffect.GoToUserContent -> displayUserContent(sideEffect.role)
            is SplashSideEffect.ShowSnackbar -> {
                stateScaffold.snackbarHostState.showSnackbar(
                    context.getString(sideEffect.message)
                )
            }
        }
    }

    Scaffold(
        scaffoldState = stateScaffold,
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            val animScale by rememberInfiniteTransition().animateFloat(
                initialValue = 0.8f,
                targetValue = 1f ,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000),
                    repeatMode = RepeatMode.Reverse
                )
            )
            val size = min(maxHeight, maxWidth) / 2
            Icon(
                painter = painterResource(R.drawable.ic_auto_stories),
                contentDescription = null,
                modifier = Modifier
                    .scale(animScale)
                    .size(size)
                    .background(MaterialTheme.colors.primary, CircleShape)
                    .padding(MaterialTheme.padding.large2),
                tint = Color.White,
            )
        }
    }

}