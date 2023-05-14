package com.example.goodsaccounting.restore_password.view_model

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.error.RestorePasswordError
import com.example.core.domain.model.restore_password.ConfirmationModel
import com.example.core.domain.model.restore_password.EmailModel
import com.example.core.domain.model.restore_password.ResetPasswordModel
import com.example.core.domain.repos.RestorePasswordRepos
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.restore_password.model.RestorePasswordEvent
import com.example.goodsaccounting.restore_password.model.RestorePasswordSideEffect
import com.example.goodsaccounting.restore_password.model.RestorePasswordState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

internal class RestorePasswordVM(
    override val di: DI,
) : AppVM<RestorePasswordState, RestorePasswordSideEffect, RestorePasswordEvent>(), DIAware {
    private val restorePasswordRepos: RestorePasswordRepos by di.instance()
    override val container: Container<RestorePasswordState, RestorePasswordSideEffect> =
        viewModelScope.container(RestorePasswordState())

    override fun onError(error: Throwable) = intent {
        when (error) {
            RestorePasswordError.UserDoesNotExist -> {
                postSideEffect(RestorePasswordSideEffect.Message(R.string.user_does_not_exist))
                reduce { state.copy(
                    isErrorEmail = true
                ) }
            }
            RestorePasswordError.InvalidCodeOrCodeTimedOut ->{
                postSideEffect(RestorePasswordSideEffect.Message(R.string.invalid_code_or_code_time_out))
                reduce { state.copy(
                    isErrorCode = true,
                ) }
            }
            else -> Log.e("RestorePasswordVM", error.message.toString())
        }
    }

    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: RestorePasswordEvent) {
        when (event) {
            is RestorePasswordEvent.InputEmail -> blockingIntent {
                reduce {
                    state.copy(
                        email = event.email,
                        isErrorEmail = false
                    )
                }
            }
            RestorePasswordEvent.PushEmail -> recovery()
            is RestorePasswordEvent.InputCode -> blockingIntent {
                if (event.code.length > 6) return@blockingIntent
                reduce {
                    state.copy(
                        code = event.code,
                        isErrorCode = false,
                    )
                }
                if(event.code.length == 6) confirmation()
            }
            RestorePasswordEvent.SendCodeAgain -> intent {
                reduce { state.copy(code = "", isErrorCode = false) }
                runCatching {
                    restorePasswordRepos.recovery(object : EmailModel {
                        override val email = state.email
                    })
                }.onFailure {
                    onError(it)
                }
            }

            RestorePasswordEvent.ClearFields -> intent {
                reduce { RestorePasswordState() }
            }

            is RestorePasswordEvent.InputNewPassword -> blockingIntent {
                reduce { state.copy(
                    newPassword = event.password,
                    isErrorNewPassword = false
                ) }
            }
            is RestorePasswordEvent.InputNewPasswordReplay ->blockingIntent {
                reduce { state.copy(
                    newPasswordReplay = event.password,
                    isErrorNewPasswordReplay = false
                ) }
            }
            RestorePasswordEvent.SetNewPassword -> resetPassword()
        }
    }

    private fun recovery() = intent {
        val isErrorEmail = !Patterns.EMAIL_ADDRESS
            .toRegex().matches(state.email)
        if (isErrorEmail) {
            reduce {
                state.copy(
                    isErrorEmail = true
                )
            }
        } else {
            reduce {
                state.copy(
                    codeIsSending = true,
                )
            }
            runCatching {
                restorePasswordRepos.recovery(object : EmailModel {
                    override val email = state.email
                })
            }.onSuccess {
                postSideEffect(RestorePasswordSideEffect.GoToInputCode)
                reduce {
                    state.copy(
                        codeIsSending = false
                    )
                }
            }.onFailure {
                reduce {
                    state.copy(codeIsSending = false)
                }
                onError(it)
            }
        }
    }
    private fun confirmation() = intent {
        runCatching {
            restorePasswordRepos.confirmation(object : ConfirmationModel {
                override val email = state.email
                override val recoveryCode = state.code
            })
        }.onSuccess {
            postSideEffect(RestorePasswordSideEffect.GoToInputNewPassword)
        }.onFailure {
            onError(it)
        }
    }
    private fun resetPassword() = intent {
        reduce { state.copy(
            isResettingPassword = true
        ) }
        val isErrorNewPassword = state.newPassword.length < 3
        val isErrorNewPasswordReplay = state.newPassword != state.newPasswordReplay
        if (isErrorNewPassword || isErrorNewPasswordReplay){
            reduce { state.copy(
                isResettingPassword = false,
                isErrorNewPassword = isErrorNewPassword,
                isErrorNewPasswordReplay = isErrorNewPasswordReplay,
            ) }
        }else{
            runCatching {
                restorePasswordRepos.resetPassword(object : ResetPasswordModel{
                    override val email = state.email
                    override val recoveryCode = state.code
                    override val password = state.newPassword
                })
            }.onSuccess {
                postSideEffect(RestorePasswordSideEffect.PasswordHasBeenChanged)
            }.onFailure {
                reduce { state.copy(
                    isResettingPassword = false
                ) }
                onError(it)
            }
        }

    }
}