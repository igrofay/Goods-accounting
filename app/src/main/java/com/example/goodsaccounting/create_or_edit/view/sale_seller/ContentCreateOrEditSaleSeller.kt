package com.example.goodsaccounting.create_or_edit.view.sale_seller

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerState
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateOrEditSaleSellerEvent
import com.example.goodsaccounting.create_or_edit.model.sale_seller.CreateSaleSellerRouting
import com.example.goodsaccounting.create_or_edit.view.common.ChoiceOfProductView

@Composable
internal fun ContentCreateOrEditSaleSeller(
    state: CreateOrEditSaleSellerState.CreateOrEdit,
    eventBase: EventBase<CreateOrEditSaleSellerEvent>,
    scaffoldState: ScaffoldState,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CreateSaleSellerRouting.FillingInFieldsCreateSaleSeller.route,
        modifier = Modifier.fillMaxSize()
    ){
        composable(CreateSaleSellerRouting.FillingInFieldsCreateSaleSeller.route){
            CreateSaleSellerFields(
                state = state,
                eventBase = eventBase,
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
                    eventBase.onEvent(CreateOrEditSaleSellerEvent.AddProduct(id))
                },
                remove = {id->
                    eventBase.onEvent(CreateOrEditSaleSellerEvent.RemoveProduct(id))
                },
                back = {
                    navController.popBackStack()
                }
            )
        }
    }
}