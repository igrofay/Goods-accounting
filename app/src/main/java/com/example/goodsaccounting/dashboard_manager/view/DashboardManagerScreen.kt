package com.example.goodsaccounting.dashboard_manager.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.dashboard_manager.model.DashboardRouting

@Composable
internal fun DashboardManagerScreen() {
    val dashboardNavController = rememberNavController()
    var isShowColumnNav by remember {
        mutableStateOf(false)
    }
    Box {
        Row {
            AnimatedVisibility(
                visible = isShowColumnNav,
                enter = expandHorizontally(
                    tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ),
                exit = shrinkHorizontally(
                    tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ),
            ) {
                ColumnNavbarView(dashboardNavController)
            }
            NavHost(
                navController = dashboardNavController,
                startDestination = DashboardRouting.Warehouse.route,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                composable(DashboardRouting.ProductsAndMaterials.route) {

                }
                composable(DashboardRouting.Warehouse.route) {

                }
                composable(DashboardRouting.WarehouseHistory.route) {

                }
            }
        }
        AnimatedMenuView(
            value = isShowColumnNav,
            onValueChange = { isShowColumnNav = it },
            durationAnim = 500,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = MaterialTheme.padding.medium2,
                    start = MaterialTheme.padding.medium1
                )
        )
    }

}