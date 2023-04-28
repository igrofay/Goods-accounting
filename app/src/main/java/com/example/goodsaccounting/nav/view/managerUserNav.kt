package com.example.goodsaccounting.nav.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.create.view.material.CreateMaterialScreen
import com.example.goodsaccounting.create.view.product.CreateProductScreen
import com.example.goodsaccounting.dashboard_manager.view.dashbord.DashboardManagerScreen
import com.example.goodsaccounting.nav.model.ManagerUserRouting
import com.example.goodsaccounting.nav.model.StartingRouting
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

                    }
                    composable(ManagerUserRouting.ComponentsWitBottomBar.Profile.route){
                        ProfileScreen()
                    }
                }
            }
        }
        composable(ManagerUserRouting.CreateMaterial.route){
            val appNavController = LocalAppNavController.current
            CreateMaterialScreen(
                exit = {
                    appNavController.popBackStack()
                }
            )
        }
        composable(ManagerUserRouting.CrateProduct.route){
            CreateProductScreen()
        }
    }

}