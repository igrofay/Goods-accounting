package com.example.goodsaccounting.nav.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.goodsaccounting.R
import com.example.goodsaccounting.analytics.view.seller.AnalyticsSellerScreen
import com.example.goodsaccounting.create_or_edit.view.sale_seller.CreateSaleSellerScreen
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
                                appNavController.navigate(SellerUserRouting.CreateOrEditSale.getAllRoute(null))
                            },
                            editSale = {idSale->
                                appNavController.navigate(SellerUserRouting.CreateOrEditSale.getAllRoute(idSale))
                            }
                        )
                    }
                    composable(SellerUserRouting.ComponentsWitBottomBar.Analytics.route){
                        AnalyticsSellerScreen()
                    }
                    composable(SellerUserRouting.ComponentsWitBottomBar.Profile.route){
                        ProfileScreen(
                            exit = {
                                appNavController.navStart()
                            }
                        )
                    }
                }
            }
        }
        composable(
            SellerUserRouting.CreateOrEditSale.getAllRoute(),
            listOf(
                navArgument(SellerUserRouting.CreateOrEditSale.arg1){ type = NavType.StringType ; nullable = true}
            )
        ){
            val appNavController = LocalAppNavController.current
            val message1 = stringResource(R.string.created)
            val message2 = stringResource(R.string.edited)
            CreateSaleSellerScreen(
                created = {
                    appNavController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(message_for_SalesSellerScreen, message1)
                    appNavController.popBackStack()
                },
                edited = {
                    appNavController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(message_for_SalesSellerScreen, message2)
                    appNavController.popBackStack()
                }
            )
        }
    }

}