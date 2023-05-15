package com.example.goodsaccounting.create_or_edit.view.material

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.goodsaccounting.common.view.state_view.LoadView
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.create_or_edit.model.material.CreateOrEditMaterialSideEffect
import com.example.goodsaccounting.create_or_edit.model.material.CreateOrEditMaterialState
import com.example.goodsaccounting.create_or_edit.view_model.CreateOrEditMaterialVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun CreateOrEditMaterialScreen(
    created: ()-> Unit,
    edited: ()-> Unit,
) {
    val createOrEditMaterialVM by rememberDIAwareViewModel<CreateOrEditMaterialVM>()
    val state by createOrEditMaterialVM.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val res = LocalContext.current.resources
    createOrEditMaterialVM.collectSideEffect{ sideEffect ->
        when(sideEffect){
            CreateOrEditMaterialSideEffect.Created -> created()
            is CreateOrEditMaterialSideEffect.ShowMessage -> {
                scaffoldState.snackbarHostState
                    .showSnackbar(res.getString(sideEffect.message))
            }
            CreateOrEditMaterialSideEffect.Edited -> edited()
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ){
        when(val tState = state){
            is CreateOrEditMaterialState.CreateOrEdit -> CreateOrEditView(
                state = tState,
                eventBase = createOrEditMaterialVM
            )
            CreateOrEditMaterialState.Load -> LoadView()
        }
    }

}