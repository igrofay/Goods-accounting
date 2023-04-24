package com.example.goodsaccounting.nav.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.R
import com.example.goodsaccounting.nav.model.NoneUserRouting
import com.example.goodsaccounting.profile.view.ProfileScreen

internal fun NavGraphBuilder.noneUserNav(appNavController: NavController) {
    composable(NoneUserRouting.route) {
        val navHostController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavbar(
                    listItems = NoneUserRouting.listBottomItemFeature,
                    navController = navHostController
                )
            }
        ) {
            NavHost(
                navController = navHostController,
                startDestination = NoneUserRouting.ListProduct.route,
                modifier = Modifier.padding(it)
            ) {
                composable(NoneUserRouting.ListProduct.route) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(id = R.string.basic_functionality_not_available),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.fillMaxWidth(0.7f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                composable(NoneUserRouting.Profile.route) {
                    ProfileScreen()
                }
            }
        }
    }
}