package com.example.goodsaccounting.dashboard_manager.view.products_and_materials

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
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
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.dashboard_manager.model.dashboard.LocalDashboardSetting
import com.example.goodsaccounting.dashboard_manager.model.products_and_materials.ProductsAndMaterialsEvent
import com.example.goodsaccounting.dashboard_manager.view_model.ProductsAndMaterialsVM
import com.example.goodsaccounting.nav.model.ManagerUserRouting
import com.example.goodsaccounting.nav.view.LocalAppNavController
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

internal const val message_for_ProductsAndMaterialsScreen = "message_for_ProductsAndMaterialsScreen"

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
internal fun ProductsAndMaterialsScreen() {
    val productsAndMaterialsState by rememberDIAwareViewModel<ProductsAndMaterialsVM>()
    val state by productsAndMaterialsState.collectAsState()
    val pagerState = rememberPagerState()
    val localCoroutine = rememberCoroutineScope()
    val dashboardSetting = LocalDashboardSetting.current
    val appNavController = LocalAppNavController.current
    val scaffoldState = rememberScaffoldState()
    val refreshState = rememberPullRefreshState(refreshing = state.isRefreshing, onRefresh = { productsAndMaterialsState.onEvent(ProductsAndMaterialsEvent.Refresh) })
    LaunchedEffect(Unit){
        productsAndMaterialsState.onEvent(ProductsAndMaterialsEvent.Refresh)
    }
    LaunchedEffect(dashboardSetting) {
        dashboardSetting.setLabel(R.string.products_and_materials)
    }
    val message = appNavController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>(message_for_ProductsAndMaterialsScreen)
    LaunchedEffect(message){
        message?.let {
            scaffoldState.snackbarHostState.showSnackbar(it)
            appNavController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(message_for_ProductsAndMaterialsScreen, null)
        }
    }
    Scaffold (
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (pagerState.currentPage) {
                        0 -> appNavController.navigate(ManagerUserRouting.CrateProduct.route){
                            popUpTo(ManagerUserRouting.ComponentsWitBottomBar.route)
                        }
                        1 -> appNavController.navigate(ManagerUserRouting.CreateMaterial.route){
                            popUpTo(ManagerUserRouting.ComponentsWitBottomBar.route)
                        }
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    ){padding->
        Box(
            modifier = Modifier
                .padding(padding)
                .pullRefresh(refreshState)
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small2),
            ) {
                TapProductsAndMaterial(
                    currentPage = pagerState.currentPage,
                    onClick = { page ->
                        localCoroutine.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }
                )
                ProductsAndMaterialsPager(
                    state = state,
                    pagerState = pagerState
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