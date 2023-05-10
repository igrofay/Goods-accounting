package com.example.goodsaccounting.create_or_edit.view.sale_seller

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.goodsaccounting.common.view.state_view.LoadView
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerState
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerSideEffect
import com.example.goodsaccounting.create_or_edit.view_model.CreateOrEditSaleSellerVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun CreateSaleSellerScreen(
    created: () -> Unit,
    edited: ()-> Unit,
) {
    val createOrEditSaleSellerVM by rememberDIAwareViewModel<CreateOrEditSaleSellerVM>()
    val state by createOrEditSaleSellerVM.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val res = LocalContext.current.resources
    createOrEditSaleSellerVM.collectSideEffect{ sideEffect->
        when(sideEffect){
            CreateOrEditSaleSellerSideEffect.Created -> created()
            is CreateOrEditSaleSellerSideEffect.Message -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    res.getString(sideEffect.stringRes)
                )
            }
            CreateOrEditSaleSellerSideEffect.Edited -> edited()
        }
    }
    when(val tState = state){
        is CreateOrEditSaleSellerState.CreateOrEdit -> ContentCreateOrEditSaleSeller(
            state = tState,
            eventBase = createOrEditSaleSellerVM,
            scaffoldState = scaffoldState
        )
        CreateOrEditSaleSellerState.Load -> Scaffold(
            scaffoldState = scaffoldState,
        ){
            LoadView()
        }
    }
}