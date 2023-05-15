package com.example.goodsaccounting.create_or_edit.view.product

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.goodsaccounting.common.view.state_view.LoadView
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductSideEffect
import com.example.goodsaccounting.create_or_edit.model.product.CreateOrEditProductState
import com.example.goodsaccounting.create_or_edit.view_model.CreateOrEditProductVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun CreateProductScreen(
    created: () -> Unit,
    edited: ()-> Unit,
) {
    val createOrEditProductVM by rememberDIAwareViewModel<CreateOrEditProductVM>()
    val state by createOrEditProductVM.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val res = LocalContext.current.resources
    createOrEditProductVM.collectSideEffect { sideEffect ->
        when (sideEffect) {
            CreateOrEditProductSideEffect.Created -> created()
            is CreateOrEditProductSideEffect.Message -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    res.getString(sideEffect.message)
                )
            }
            CreateOrEditProductSideEffect.Edited -> edited()
        }
    }
   when(val tState = state){
       is CreateOrEditProductState.CreateOrEdit -> CreateOrEditView(
           state = tState,
           eventBase = createOrEditProductVM,
           scaffoldState = scaffoldState,
       )
       CreateOrEditProductState.Load -> LoadView()
   }


}