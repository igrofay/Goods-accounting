package com.example.goodsaccounting.profile.view

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.goodsaccounting.common.view.state_view.LoadView
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.profile.model.ProfileSideEffect
import com.example.goodsaccounting.profile.model.ProfileState
import com.example.goodsaccounting.profile.view_model.ProfileVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun ProfileScreen() {
    val scaffoldState = rememberScaffoldState()
    val profileVM by rememberDIAwareViewModel<ProfileVM>()
    val state by profileVM.collectAsState()
    val res = LocalContext.current.resources
    profileVM.collectSideEffect{side ->
        when(side){
            is ProfileSideEffect.ShowMessage -> scaffoldState.snackbarHostState
                .showSnackbar(res.getString(side.message))
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        when (state) {
            ProfileState.LoadProfileInfo -> LoadView()
            is ProfileState.UserData, is ProfileState.EditingUserData -> {
                val tState = (state as? ProfileState.UserData)
                    ?: (state as ProfileState.EditingUserData)
                ProfileInfoView(tState, profileVM)
            }
        }
    }
}