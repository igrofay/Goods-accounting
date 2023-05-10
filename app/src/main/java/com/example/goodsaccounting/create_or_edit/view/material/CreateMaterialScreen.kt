package com.example.goodsaccounting.create_or_edit.view.material

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.goodsaccounting.common.view.button.CreateButton
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.create_or_edit.model.material.CreateMaterialEvent
import com.example.goodsaccounting.create_or_edit.model.material.CreateMaterialSideEffect
import com.example.goodsaccounting.create_or_edit.view_model.CreateMaterialVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun CreateMaterialScreen(
    exit: ()-> Unit
) {
    val createMaterialVM by rememberDIAwareViewModel<CreateMaterialVM>()
    val state by createMaterialVM.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val res = LocalContext.current.resources
    createMaterialVM.collectSideEffect{sideEffect ->
        when(sideEffect){
            CreateMaterialSideEffect.MaterialCreated -> exit()
            is CreateMaterialSideEffect.ShowMessage -> {
                scaffoldState.snackbarHostState
                    .showSnackbar(res.getString(sideEffect.message))
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MainSettingView(
                state = state,
                eventBase = createMaterialVM,
            )
            Spacer(modifier = Modifier.weight(1f))
            CreateButton(
                isCreating = state.isCreating
            ){
                createMaterialVM.onEvent(CreateMaterialEvent.Create)
            }
        }
    }

}