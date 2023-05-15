package com.example.goodsaccounting.profile.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.use_case.user.ExitUseCase
import com.example.core.domain.use_case.user.GetUserFlowUseCase
import com.example.core.domain.use_case.user.UpdateUserDataUseCase
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.profile.model.*
import com.example.goodsaccounting.profile.model.ProfileState.Companion.fromModelToUserData
import com.example.goodsaccounting.profile.model.ProfileState.Companion.toEditMode
import kotlinx.coroutines.flow.catch
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

internal class ProfileVM(
    override val di: DI
) : AppVM<ProfileState, ProfileSideEffect, ProfileEvent>(), DIAware {
    private val getUserFlowUseCase: GetUserFlowUseCase by di.instance()
    private val updateUserDataUseCase: UpdateUserDataUseCase by di.instance()
    override val container: Container<ProfileState, ProfileSideEffect> = viewModelScope
        .container(ProfileState.LoadProfileInfo) { loadProfile() }
    private val exitUseCase: ExitUseCase by di.instance()
    @OptIn(OrbitExperimental::class)
    override fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.SelectImage -> intent {
                when (val lastState = state) {
                    is ProfileState.EditingUserData -> reduce {
                        lastState.copy(imageUrl = event.uri)
                    }

                    ProfileState.LoadProfileInfo -> return@intent
                    is ProfileState.UserData -> reduce {
                        lastState.toEditMode().copy(imageUrl = event.uri)
                    }
                }
            }

            is ProfileEvent.InputFirstname -> blockingIntent {
                when (val lastState = state) {
                    is ProfileState.EditingUserData -> reduce {
                        lastState.copy(firstname = event.firstname)
                    }

                    ProfileState.LoadProfileInfo -> return@blockingIntent
                    is ProfileState.UserData -> reduce {
                        lastState.toEditMode().copy(firstname = event.firstname)
                    }
                }
            }

            is ProfileEvent.InputLastname -> blockingIntent {
                when (val lastState = state) {
                    is ProfileState.EditingUserData -> reduce {
                        lastState.copy(lastname = event.lastname)
                    }

                    ProfileState.LoadProfileInfo -> return@blockingIntent
                    is ProfileState.UserData -> reduce {
                        lastState.toEditMode().copy(lastname = event.lastname)
                    }
                }
            }

            is ProfileEvent.InputPatronymic -> blockingIntent {
                when (val lastState = state) {
                    is ProfileState.EditingUserData -> reduce {
                        lastState.copy(patronymic = event.patronymic)
                    }

                    ProfileState.LoadProfileInfo -> return@blockingIntent
                    is ProfileState.UserData -> reduce {
                        lastState.toEditMode().copy(patronymic = event.patronymic)
                    }
                }
            }

            is ProfileEvent.InputPhone -> blockingIntent {
                if (event.phone.length > 10) return@blockingIntent
                when (val lastState = state) {
                    is ProfileState.EditingUserData -> reduce {
                        lastState.copy(phone = event.phone)
                    }

                    ProfileState.LoadProfileInfo -> return@blockingIntent
                    is ProfileState.UserData -> reduce {
                        lastState.toEditMode().copy(phone = event.phone)
                    }
                }
            }

            ProfileEvent.Save -> intent {
                val lastState = (state as? ProfileState.EditingUserData) ?: return@intent
                val isErrorFirstname = lastState.firstname.length <= 1
                val isErrorLastname = lastState.lastname.length <= 2
                val isErrorPatronymic = lastState.patronymic.length <= 2
                val isErrorPhone = lastState.phone.length != 10
                val newState = lastState.copy(
                    // user data
                    isErrorFirstname = isErrorFirstname,
                    isErrorLastname = isErrorLastname,
                    isErrorPatronymic = isErrorPatronymic,
                    isErrorPhone = isErrorPhone,
                    isUpdating = !(
                            isErrorFirstname || isErrorLastname
                                    || isErrorPatronymic || isErrorPhone
                            )
                )
                reduce { newState }
                if (newState.isUpdating) {
                    updateUserDataUseCase.execute(newState).onSuccess {
                        reduce { it.fromModelToUserData() }
                    }.onFailure {
                        (state as? ProfileState.EditingUserData)
                            ?.copy(isUpdating = false)?.let { editing ->
                                reduce { editing }
                            }
                        postSideEffect(ProfileSideEffect.ShowMessage(R.string.network_problems))
                        onError(it)
                    }
                }
            }

            ProfileEvent.Exit -> intent {
                exitUseCase.execute().onSuccess {
                    postSideEffect(ProfileSideEffect.ExitProfile)
                }
            }
        }
    }

    override fun onError(error: Throwable) {
        when (error) {
            else -> Log.e("ProfileVM", error.message.toString())
        }
    }

    private fun loadProfile() = intent(false) {
        getUserFlowUseCase.execute()
            .catch { onError(it) }
            .collect { userModel ->
                when (state) {
                    ProfileState.LoadProfileInfo -> reduce {
                        userModel.fromModelToUserData()
                    }

                    is ProfileState.UserData -> reduce {
                        userModel.fromModelToUserData()
                    }

                    is ProfileState.EditingUserData -> {}
                }
            }
    }
}