package com.example.goodsaccounting.nav.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.goodsaccounting.R
import com.example.goodsaccounting.create.view.sale_seller.CreateSaleSellerScreen
import com.example.goodsaccounting.dashboard_manager.view.products_and_materials.message_for_ProductsAndMaterialsScreen
import com.example.goodsaccounting.nav.model.SellerUserRouting
import com.example.goodsaccounting.profile.view.ProfileScreen
import com.example.goodsaccounting.sales_seller.view.SalesSellerScreen
import com.example.goodsaccounting.sales_seller.view.message_for_SalesSellerScreen

internal fun NavGraphBuilder.sellerUserNav() {
    navigation(
        startDestination =  SellerUserRouting.ComponentsWitBottomBar.route,
        route = SellerUserRouting.route,
    ){
        composable(SellerUserRouting.ComponentsWitBottomBar.route) {
            val navController = rememberNavController()
            val appNavController = LocalAppNavController.current
            Scaffold(
                bottomBar = {
                    BottomNavbar(
                        listItems = SellerUserRouting.ComponentsWitBottomBar.listBottomItemFeature,
                        navController = navController
                    )
                }
            ) {
                NavHost(
                    navController= navController,
                    startDestination = SellerUserRouting.ComponentsWitBottomBar.Sales.route,
                    modifier = Modifier.padding(it)
                ) {
                    composable(SellerUserRouting.ComponentsWitBottomBar.Sales.route){
                        SalesSellerScreen(
                            createSale = {
                                appNavController.navigate(SellerUserRouting.CreateSale.route)
                            },
                            editSale = {idSale->

                            }
                        )
                    }
                    composable(SellerUserRouting.ComponentsWitBottomBar.Analytics.route){

                    }
                    composable(SellerUserRouting.ComponentsWitBottomBar.Profile.route){
                        ProfileScreen()
                    }
                }
            }
        }
        composable(SellerUserRouting.CreateSale.route){
            val appNavController = LocalAppNavController.current
            val message = stringResource(R.string.created)
            CreateSaleSellerScreen(
                created = {
                    appNavController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(message_for_SalesSellerScreen, message)
                    appNavController.popBackStack()
                }
            )
        }
    }

}