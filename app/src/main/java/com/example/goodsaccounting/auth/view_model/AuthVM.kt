package com.example.goodsaccounting.auth.view_model

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.auth.SignInModel
import com.example.core.domain.model.auth.SignUpModel
import com.example.core.domain.model.error.AuthError
import com.example.core.domain.use_case.auth.SignInUseCase
import com.example.core.domain.use_case.auth.SignUpUseCase
import com.example.goodsaccounting.R
import com.example.goodsaccounting.auth.model.AuthEvent
import com.example.goodsaccounting.auth.model.AuthSideEffect
import com.example.goodsaccounting.auth.model.AuthState
import com.example.goodsaccounting.auth.model.AuthType
import com.example.goodsaccounting.common.view_model.AppVM
import kotlinx.coroutines.delay
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

internal class AuthVM(
    override val di: DI,
) : AppVM<AuthState, AuthSideEffect, AuthEvent>(), DIAware {

    private val signUpUseCase : SignUpUseCase by di.instance()
    private val signInUseCase: SignInUseCase by di.instance()
    override val container: Container<AuthState, AuthSideEffect> =
        viewModelScope.container(AuthState())

    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.InputEmail -> blockingIntent {
                reduce {
                    state.copy(
                        email = event.email,
                        isErrorEmail = false,
                    )
                }
            }
            is AuthEvent.InputFirstname -> blockingIntent {
                reduce {
                    state.copy(
                        firstname = event.firstname,
                        isErrorFirstname = false,
                    )
                }
            }
            is AuthEvent.InputLastname -> blockingIntent {
                reduce {
                    state.copy(
                        lastname = event.lastname,
                        isErrorLastname = false,
                    )
                }
            }
            is AuthEvent.InputPassword -> blockingIntent {
                reduce {
                    state.copy(
                        password = event.password,
                        isErrorPassword = false,
                    )
                }
            }
            is AuthEvent.InputPatronymic -> blockingIntent {
                reduce {
                    state.copy(
                        patronymic = event.patronymic,
                        isErrorPatronymic = false,
                    )
                }
            }
            is AuthEvent.InputPhone -> blockingIntent {
                if(event.phone.length > 10 ) return@blockingIntent
                reduce {
                    state.copy(
                        phone = event.phone,
                        isErrorPhone = false,
                    )
                }
            }
            AuthEvent.SetSignIn -> blockingIntent {
                reduce {
                    state.copy(
                        type = AuthType.SignIn
                    )
                }
            }
            AuthEvent.SetSingUp -> blockingIntent {
                reduce {
                    state.copy(
                        type = AuthType.SignUp
                    )
                }
            }
            AuthEvent.Authorization -> intent {
                when(state.type){
                    AuthType.SignIn -> signIn()
                    AuthType.SignUp -> signUp()
                }
            }

        }
    }


    private fun signIn() = intent {
        val isErrorEmail = ! Patterns.EMAIL_ADDRESS
            .toRegex().matches(state.email)
        val isErrorPassword = state.password.length < 3
        val newState = state.copy(
            isErrorEmail = isErrorEmail,
            isErrorPassword = isErrorPassword,
            isLoading = !(isErrorEmail || isErrorPassword),
        )
        reduce { newState }
        if (newState.isLoading){
            signInUseCase.execute(
                object : SignInModel {
                    override val email  = newState.email
                    override val password = newState.password
                }
            ).onSuccess { role ->
                postSideEffect(AuthSideEffect.GoToUserContent(role))
            }.onFailure(::onError)
        }
    }

    private fun signUp() = intent {
        // user data
        val isErrorFirstname = state.firstname.length <= 1
        val isErrorLastname = state.lastname.length <= 2
        val isErrorPatronymic = state.patronymic.length <= 2
        val isErrorPhone = state.phone.length != 10
        // for auth
        val isErrorEmail = ! Patterns.EMAIL_ADDRESS
            .toRegex().matches(state.email)
        val isErrorPassword = state.password.length < 3
        val newState = state.copy(
            // for auth
            isErrorEmail = isErrorEmail,
            isErrorPassword = isErrorPassword,
            // user data
            isErrorFirstname = isErrorFirstname,
            isErrorLastname = isErrorLastname,
            isErrorPatronymic = isErrorPatronymic,
            isErrorPhone = isErrorPhone,
            isLoading = !(
                    isErrorEmail || isErrorPassword
                            || isErrorFirstname || isErrorLastname
                            || isErrorPatronymic || isErrorPhone
                    ),
        )
        reduce { newState }
        if (newState.isLoading){
            signUpUseCase.execute(
                object : SignUpModel {
                    override val firstname = newState.firstname
                    override val lastname = newState.lastname
                    override val patronymic = newState.patronymic
                    override val phone = newState.phone
                    override val email = newState.email
                    override val password = newState.password
                }
            ).onSuccess { role ->
                postSideEffect(AuthSideEffect.GoToUserContent(role))
            }.onFailure(::onError)
        }
    }

    override fun onError(error: Throwable) = intent {
        var newState = state.copy(isLoading = false)
        when(error){
            is AuthError->when(error){
                AuthError.AccountAlreadyRegistered -> {
                    newState = newState.copy(isErrorEmail = true)
                    postSideEffect(
                        AuthSideEffect.ShowMessage(R.string.account_already_registered)
                    )
                }
                AuthError.AccountNotFound -> {
                    newState = newState.copy(isErrorEmail = true)
                    postSideEffect(
                        AuthSideEffect.ShowMessage(R.string.account_not_found)
                    )
                }
                AuthError.IncorrectPassword -> {
                    newState = newState.copy(isErrorPassword = true)
                    postSideEffect(
                        AuthSideEffect.ShowMessage(R.string.incorrect_password)
                    )
                }
                AuthError.NullRefreshToken -> Unit
                AuthError.FailedToRestoreSession -> Unit
            }
            else -> Log.e("AuthVM", error.message.toString())
        }
        reduce { newState }
    }
}