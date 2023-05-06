package com.example.goodsaccounting.create.view.sale_seller

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.create.model.sale_seller.CreateSaleSellerEvent
import com.example.goodsaccounting.create.model.sale_seller.CreateSaleSellerRouting
import com.example.goodsaccounting.create.model.sale_seller.CreateSaleSellerSideEffect
import com.example.goodsaccounting.create.view.common.ChoiceOfProductView
import com.example.goodsaccounting.create.view_model.CreateSaleSellerVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun CreateSaleSellerScreen(
    created: () -> Unit,
) {
    val createSaleSellerVM by rememberDIAwareViewModel<CreateSaleSellerVM>()
    val state by createSaleSellerVM.collectAsState()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val res = LocalContext.current.resources
    createSaleSellerVM.collectSideEffect{sideEffect->
        when(sideEffect){
            CreateSaleSellerSideEffect.Created -> created()
            is CreateSaleSellerSideEffect.Message -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    res.getString(sideEffect.stringRes)
                )
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = CreateSaleSellerRouting.FillingInFieldsCreateSaleSeller.route,
        modifier = Modifier.fillMaxSize()
    ){
        composable(CreateSaleSellerRouting.FillingInFieldsCreateSaleSeller.route){
            CreateSaleSellerFields(
                state = state,
                eventBase = createSaleSellerVM,
                scaffoldState = scaffoldState,
                choiceOfProducts = {
                    navController.navigate(CreateSaleSellerRouting.ChoiceOfProducts.route){
                        popUpTo(CreateSaleSellerRouting.FillingInFieldsCreateSaleSeller.route)
                    }
                }
            )
        }
        composable(CreateSaleSellerRouting.ChoiceOfProducts.route){
            ChoiceOfProductView(
                products = state.products,
                listProductForAdd = state.listProductForAdd,
                add = { id->
                    createSaleSellerVM.onEvent(CreateSaleSellerEvent.AddProduct(id))
                },
                remove = {id->
                    createSaleSellerVM.onEvent(CreateSaleSellerEvent.RemoveProduct(id))
                },
                back = {
                    navController.popBackStack()
                }
            )
        }
    }
}