package com.example.goodsaccounting.auth.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.auth.model.AuthEvent
import com.example.goodsaccounting.auth.model.AuthSideEffect
import com.example.goodsaccounting.auth.model.AuthType
import com.example.goodsaccounting.auth.view_model.AuthVM
import com.example.goodsaccounting.common.view.button.CustomButton
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun AuthScreen(
    goToRestorePassword: () -> Unit,
    goToUserContent: (RoleLevel) -> Unit
) {
    val authVM by rememberDIAwareViewModel<AuthVM>()
    val scaffoldState = rememberScaffoldState()
    val state by authVM.collectAsState()
    val res = LocalContext.current.resources
    authVM.collectSideEffect{
        when(it){
            is AuthSideEffect.GoToUserContent -> goToUserContent(it.roleLevel)
            is AuthSideEffect.ShowMessage -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = res.getString(it.message)
                )
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(top = MaterialTheme.padding.large2)
                .padding(horizontal = MaterialTheme.padding.medium2)
        ) {
            WelcomeView()
            Spacer(modifier = Modifier.weight(0.2f))
            AuthInputFields(state = state, eventBase = authVM)
            if (state.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = MaterialTheme.padding.medium2)
                )
            }else{
                CustomButton(
                    label = when (state.type) {
                        AuthType.SignIn -> stringResource(id = R.string.sign_in)
                        AuthType.SignUp -> stringResource(id = R.string.sign_up)
                    },
                    modifier = Modifier.padding(vertical = MaterialTheme.padding.medium2)
                ) {
                    authVM.onEvent(AuthEvent.Authorization)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            ActionButtons(state = state, eventBase = authVM, restorePassword = goToRestorePassword)
        }
    }
}