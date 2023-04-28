package com.example.goodsaccounting.nav.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.auth.view.AuthScreen
import com.example.goodsaccounting.nav.model.AdministratorUserRouting
import com.example.goodsaccounting.nav.model.StartingRouting
import com.example.goodsaccounting.splash.view.SplashScreen

internal fun NavGraphBuilder.staringNav() {
    navigation(
        startDestination = StartingRouting.Splash.route,
        route = StartingRouting.route,
    ) {
        composable(StartingRouting.Splash.route) {
            val appNavController = LocalAppNavController.current
            SplashScreen(
                needAuth = {
                    appNavController.navWithClearStack(StartingRouting.Auth.route)
                },
                displayUserContent = {
                    appNavController.navigateToUserRole(it)
                }
            )
        }
        composable(StartingRouting.Auth.route) {
            val appNavController = LocalAppNavController.current
            AuthScreen(
                goToRestorePassword = {
                    appNavController.navigate(
                        StartingRouting.RestorePassword.route,
                    ){
                        popUpTo(StartingRouting.Auth.route)
                    }
                },
                goToUserContent = {
                    appNavController.navigateToUserRole(it)
                }
            )
        }
        composable(StartingRouting.RestorePassword.route){

        }
    }
}