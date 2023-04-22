package com.example.goodsaccounting.nav.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.dashboard_manager.view.DashboardManagerScreen
import com.example.goodsaccounting.nav.model.ManagerUserRouting
import com.example.goodsaccounting.profile.view.ProfileScreen

internal fun NavGraphBuilder.managerUserNav(appNavController: NavController){
    composable(ManagerUserRouting.route) {
        val navHostController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavBar(
                    listItems = ManagerUserRouting.listBottomItemFeature,
                    navController = navHostController
                )
            }
        ) {
            NavHost(
                navController = navHostController,
                startDestination = ManagerUserRouting.Dashboard.route,
                modifier = Modifier.padding(it)
            ){
                composable(ManagerUserRouting.Dashboard.route){
                    DashboardManagerScreen()
                }
                composable(ManagerUserRouting.Analytics.route){

                }
                composable(ManagerUserRouting.Profile.route,){
                    ProfileScreen()
                }
            }
        }
    }
}