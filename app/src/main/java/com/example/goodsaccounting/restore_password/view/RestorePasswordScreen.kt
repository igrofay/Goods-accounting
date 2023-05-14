package com.example.goodsaccounting.restore_password.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.restore_password.model.RestorePasswordEvent
import com.example.goodsaccounting.restore_password.model.RestorePasswordRouting
import com.example.goodsaccounting.restore_password.model.RestorePasswordSideEffect
import com.example.goodsaccounting.restore_password.view_model.RestorePasswordVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun RestorePasswordScreen(
    exit: ()-> Unit,
) {
    val restorePasswordVM by rememberDIAwareViewModel<RestorePasswordVM>()
    val state by restorePasswordVM.collectAsState()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val res = LocalContext.current.resources
    restorePasswordVM.collectSideEffect{sideEffect->
        when(sideEffect){
            RestorePasswordSideEffect.GoToInputCode ->
                navController.navigate(RestorePasswordRouting.InputCode.route)
            RestorePasswordSideEffect.GoToInputNewPassword ->
                navController.navigate(RestorePasswordRouting.InputNewPassword.route){
                    popUpTo(RestorePasswordRouting.InputEmail.route)
                }
            is RestorePasswordSideEffect.Message -> scaffoldState.snackbarHostState
                .showSnackbar(res.getString(sideEffect.message))

            RestorePasswordSideEffect.PasswordHasBeenChanged -> exit()
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        NavHost(
            navController = navController,
            startDestination = RestorePasswordRouting.InputEmail.route,
            modifier = Modifier
                .padding(it)
                .padding(top = MaterialTheme.padding.large2)
        ){
            composable(RestorePasswordRouting.InputEmail.route){
                InputEmailView(state = state, eventBase = restorePasswordVM)
            }
            composable(RestorePasswordRouting.InputCode.route){
                BackHandler {
                    restorePasswordVM.onEvent(RestorePasswordEvent.InputCode(""))
                    navController.popBackStack()
                }
                InputCodeView(
                    state = state,
                    eventBase = restorePasswordVM,
                    back = {
                        restorePasswordVM.onEvent(RestorePasswordEvent.InputCode(""))
                        navController.popBackStack(
                            RestorePasswordRouting.InputEmail.route,
                            inclusive = false
                        )
                    }
                )
            }
            composable(RestorePasswordRouting.InputNewPassword.route){
                BackHandler {
                    restorePasswordVM.onEvent(RestorePasswordEvent.ClearFields)
                    navController.popBackStack()
                }
                InputNewPasswordView(
                    state = state,
                    eventBase = restorePasswordVM,
                )
            }
        }
    }

}