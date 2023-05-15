package com.example.goodsaccounting.nav.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.goodsaccounting.R
import com.example.goodsaccounting.analytics.view.manager.AnalyticsManagerScreen
import com.example.goodsaccounting.create_or_edit.view.material.CreateOrEditMaterialScreen
import com.example.goodsaccounting.create_or_edit.view.product.CreateProductScreen
import com.example.goodsaccounting.create_or_edit.view.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialScreen
import com.example.goodsaccounting.dashboard_manager.view.dashbord.DashboardManagerScreen
import com.example.goodsaccounting.dashboard_manager.view.products_and_materials.message_for_ProductsAndMaterialsScreen
import com.example.goodsaccounting.dashboard_manager.view.warehouse_history.message_for_WarehouseHistoryScreen
import com.example.goodsaccounting.nav.model.ManagerUserRouting
import com.example.goodsaccounting.profile.view.ProfileScreen

internal fun NavGraphBuilder.managerUserNav(){
    navigation(
        startDestination = ManagerUserRouting.ComponentsWitBottomBar.route,
        route = ManagerUserRouting.route,
    ) {
        composable(ManagerUserRouting.ComponentsWitBottomBar.route) {
            val navHostController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavbar(
                        listItems = ManagerUserRouting.ComponentsWitBottomBar.listBottomItemFeature,
                        navController = navHostController
                    )
                }
            ) {
                NavHost(
                    navController = navHostController,
                    startDestination = ManagerUserRouting.ComponentsWitBottomBar.Dashboard.route,
                    modifier = Modifier.padding(it)
                ){
                    composable(ManagerUserRouting.ComponentsWitBottomBar.Dashboard.route){
                        DashboardManagerScreen()
                    }
                    composable(ManagerUserRouting.ComponentsWitBottomBar.Analytics.route){
                        AnalyticsManagerScreen()
                    }
                    composable(ManagerUserRouting.ComponentsWitBottomBar.Profile.route){
                        val appNavController = LocalAppNavController.current
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
            ManagerUserRouting.CreateOrEditMaterial.getAllRoute(),
            listOf(
                navArgument(ManagerUserRouting.CreateOrEditMaterial.arg1){ nullable = true}
            )
        ){
            val appNavController = LocalAppNavController.current
            val message1 = stringResource(R.string.material_created)
            val message2 = stringResource(R.string.edited)
            CreateOrEditMaterialScreen(
                created = {
                    appNavController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(message_for_ProductsAndMaterialsScreen, message1)
                    appNavController.popBackStack()
                },
                edited = {
                    appNavController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(message_for_ProductsAndMaterialsScreen, message2)
                    appNavController.popBackStack()
                }
            )
        }
        composable(
            ManagerUserRouting.CrateOrEditProduct.getAllRoute(),
            listOf(
                navArgument(ManagerUserRouting.CrateOrEditProduct.arg1){ nullable = true}
            )
        ){
            val appNavController = LocalAppNavController.current
            val message1 = stringResource(R.string.product_created)
            val message2 = stringResource(R.string.edited)
            CreateProductScreen(
                created = {
                    appNavController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(message_for_ProductsAndMaterialsScreen, message1)
                    appNavController.popBackStack()
                },
                edited = {
                    appNavController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(message_for_ProductsAndMaterialsScreen, message2)
                    appNavController.popBackStack()
                }
            )
        }
        composable(
            ManagerUserRouting.CreateReceiptOrWriteOfMaterial.allRoute(),
            arguments = listOf(
                navArgument( ManagerUserRouting.CreateReceiptOrWriteOfMaterial.arg1,){
                    type = NavType.BoolType
                }
            ),
        ){
            val appNavController = LocalAppNavController.current
            val res = LocalContext.current.resources
            CreateReceiptOrWriteOfMaterialScreen(
                created = {
                    appNavController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(message_for_WarehouseHistoryScreen, res.getString(R.string.created))
                    appNavController.popBackStack()
                }
            )
        }
    }

}