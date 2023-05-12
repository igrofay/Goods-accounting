package com.example.goodsaccounting.dashboard_manager.view.warehouse_history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.dashboard_manager.model.dashboard.LocalDashboardSetting
import com.example.goodsaccounting.dashboard_manager.model.products_and_materials.ProductsAndMaterialsEvent
import com.example.goodsaccounting.dashboard_manager.model.warehouse_history.WarehouseHistoryEvent
import com.example.goodsaccounting.dashboard_manager.view.products_and_materials.message_for_ProductsAndMaterialsScreen
import com.example.goodsaccounting.dashboard_manager.view_model.WarehouseHistoryVM
import com.example.goodsaccounting.nav.model.ManagerUserRouting
import com.example.goodsaccounting.nav.view.LocalAppNavController
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

const val message_for_WarehouseHistoryScreen = "message_for_WarehouseHistoryScreen"


@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
internal fun WarehouseHistoryScreen() {
    val warehouseHistoryVM by rememberDIAwareViewModel<WarehouseHistoryVM>()
    val state by warehouseHistoryVM.collectAsState()
    val dashboardSetting = LocalDashboardSetting.current
    LaunchedEffect(dashboardSetting){
        dashboardSetting.setLabel(R.string.warehouse_history)
    }
    val pagerState = rememberPagerState()
    val localCoroutine = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val refreshState = rememberPullRefreshState(refreshing = state.isRefreshing, onRefresh = { warehouseHistoryVM.onEvent(WarehouseHistoryEvent.Refresh)})
    LaunchedEffect(Unit){
        warehouseHistoryVM.onEvent(WarehouseHistoryEvent.Refresh)
    }
    val appNavController = LocalAppNavController.current
    val message = appNavController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>(message_for_WarehouseHistoryScreen)
    LaunchedEffect(message){
        message?.let {
            scaffoldState.snackbarHostState.showSnackbar(it)
            appNavController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(message_for_WarehouseHistoryScreen, null)
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            AnimatedVisibility(
                visible = pagerState.currentPage in listOf(1,2),
                exit = fadeOut() + scaleOut(),
                enter =  fadeIn() + scaleIn()
            ) {
                FloatingActionButton(
                    onClick = {
                        when(pagerState.currentPage){
                            1-> appNavController.navigate(
                                ManagerUserRouting.CreateReceiptOrWriteOfMaterial.allRoute(true)
                            )
                            2-> appNavController.navigate(
                                ManagerUserRouting.CreateReceiptOrWriteOfMaterial.allRoute(false)
                            )
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ){
            Column(
                modifier = Modifier
                    .pullRefresh(refreshState),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TapWarehouseHistory(
                    currentPage = pagerState.currentPage,
                    onClick = {page->
                        localCoroutine.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }
                )
                WarehouseHistoryPager(
                    pagerState = pagerState,
                    state = state,
                )
            }
            PullRefreshIndicator(
                state.isRefreshing,
                refreshState,
                Modifier.align(Alignment.TopCenter),
                contentColor = MaterialTheme.colors.primary
            )
        }
    }


}