package com.example.goodsaccounting.nav.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.nav.model.AdministratorUserRouting
import com.example.goodsaccounting.profile.view.ProfileScreen
import com.example.goodsaccounting.user_administration.view.UserAdministrationScreen

internal fun NavGraphBuilder.administratorUserNav(appNavController: NavController){
    composable(AdministratorUserRouting.route){
        val navHostController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavbar(
                    listItems = AdministratorUserRouting.listBottomItemFeature,
                    navController = navHostController
                )
            }
        ) {
            NavHost(
                navController = navHostController,
                startDestination = AdministratorUserRouting.ListUser.route,
                modifier = Modifier.padding(it)
            ){
                composable(AdministratorUserRouting.ListUser.route){
                    UserAdministrationScreen()
                }
                composable(AdministratorUserRouting.Profile.route){
                    ProfileScreen()
                }
            }
        }
    }
}