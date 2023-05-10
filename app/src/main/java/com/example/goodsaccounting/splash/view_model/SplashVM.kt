package com.example.goodsaccounting.splash.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.error.AuthError
import com.example.core.domain.use_case.auth.RestoreSessionUseCase
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.model.mvi.UIEvent
import com.example.goodsaccounting.common.model.mvi.UIState
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.splash.model.SplashSideEffect
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

internal class SplashVM(
    override val di: DI,
) : AppVM<UIState.Absence, SplashSideEffect, UIEvent.Absence>(), DIAware {
    private val restoreSessionUseCase: RestoreSessionUseCase by di.instance()


    override val container: Container<UIState.Absence, SplashSideEffect> =
        viewModelScope.container(UIState.Absence) { load() }

    override fun onEvent(event: UIEvent.Absence) {}

    private fun load() = intent {
        restoreSessionUseCase.execute()
            .onSuccess { role ->
                postSideEffect(SplashSideEffect.GoToUserContent(role))
            }.onFailure(::onError)
    }


    override fun onError(error: Throwable) = intent {
        when (error) {
            AuthError.NullRefreshToken -> postSideEffect(SplashSideEffect.GoToAuthentication)
            AuthError.FailedToRestoreSession -> postSideEffect(SplashSideEffect.GoToAuthentication)
            else -> {
                Log.e("SplashVM>>", error.message.toString())
                postSideEffect(SplashSideEffect.ShowSnackbar(message = R.string.network_problems))
            }
        }
    }

}