package com.example.goodsaccounting.dashboard_manager.view.dashbord

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
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.dashboard_manager.model.DashboardRouting
import com.example.goodsaccounting.dashboard_manager.model.DashboardSetting
import com.example.goodsaccounting.dashboard_manager.model.LocalDashboardSetting
import com.example.goodsaccounting.dashboard_manager.view.products_and_materials.ProductsAndMaterialsScreen
import com.example.goodsaccounting.dashboard_manager.view.warehouse.WarehouseScreen
import com.example.goodsaccounting.dashboard_manager.view.warehouse_history.WarehouseHistoryScreen

@Composable
internal fun DashboardManagerScreen() {
    val dashboardNavController = rememberNavController()
    var isShowColumnNav by remember {
        mutableStateOf(false)
    }
    CompositionLocalProvider(
        LocalDashboardSetting provides DashboardSetting()
    ) {
        val dashboardSetting = LocalDashboardSetting.current
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
                        ProductsAndMaterialsScreen()
                    }
                    composable(DashboardRouting.Warehouse.route) {
                        WarehouseScreen()
                    }
                    composable(DashboardRouting.WarehouseHistory.route) {
                        WarehouseHistoryScreen()
                    }
                }
            }
            AnimatedTopBarView(
                value = isShowColumnNav,
                onValueChange = { isShowColumnNav = it },
                durationAnim = 500,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        top = MaterialTheme.padding.medium2,
                        start = MaterialTheme.padding.medium1
                    ),
                label = dashboardSetting.labelTopBar?.let {stringRes->
                    stringResource(stringRes)
                }
            )
        }
    }

}