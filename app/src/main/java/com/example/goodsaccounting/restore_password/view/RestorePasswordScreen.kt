package com.example.goodsaccounting.restore_password.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.restore_password.model.RestorePasswordRouting

@Composable
internal fun RestorePasswordScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RestorePasswordRouting.InputEmail.route
    ){
        composable(RestorePasswordRouting.InputEmail.route){

        }
        composable(RestorePasswordRouting.InputCode.route){

        }
        composable(RestorePasswordRouting.InputNewPassword.route){

        }
    }
}