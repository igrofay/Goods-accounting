package com.example.goodsaccounting.user_administration.view_model

import androidx.lifecycle.viewModelScope
import com.example.core.domain.repos.AdminPanelRepos
import com.example.core.domain.use_case.admin.GetUsersUseCase
import com.example.goodsaccounting.common.view_model.AppVM
import com.example.goodsaccounting.user_administration.model.UserAdministrationEvent
import com.example.goodsaccounting.user_administration.model.UserAdministrationSideEffect
import com.example.goodsaccounting.user_administration.model.UserAdministrationState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

internal class UserAdministrationVM(
    override val di: DI,
) : AppVM<UserAdministrationState, UserAdministrationSideEffect, UserAdministrationEvent>(),
    DIAware {
    private val adminPanelRepos: AdminPanelRepos by di.instance()
    private val getUsersUseCase: GetUsersUseCase by di.instance()

    override val container: Container<UserAdministrationState, UserAdministrationSideEffect> =
        viewModelScope
            .container(UserAdministrationState()) { loadUsers() }

    override fun onEvent(event: UserAdministrationEvent) = intent {
        when (event) {
            UserAdministrationEvent.Refresh -> {
                reduce { state.copy(isRefreshing = true) }
                loadUsers()
            }
            is UserAdministrationEvent.OpenUserForChangeRole ->  {
                reduce {
                    state.copy(changeUserRole = event.userModel)
                }
            }
            UserAdministrationEvent.CloseChangeUserRole ->  {
                reduce { state.copy(changeUserRole = null, newUserRole = null) }
            }
            is UserAdministrationEvent.SaveNewUserRole ->  {
                state.newUserRole?.let {newRole ->
                    state.changeUserRole?.email?.let { emailUser->
                        adminPanelRepos.updateUserRole(emailUser, newRole)
                    }
                }
                reduce { state.copy(changeUserRole = null, newUserRole = null) }
                loadUsers()
            }
            is UserAdministrationEvent.SelectedNewUserRole ->  {
                reduce { state.copy(newUserRole = event.roleLevel) }
            }
        }
    }

    override fun onError(error: Throwable) {

    }

    private fun loadUsers() = intent {
        getUsersUseCase.execute().onSuccess { users ->
            reduce {
                state.copy(
                    users = users,
                    isRefreshing = false,
                )
            }
        }.onFailure(::onError)
    }
}