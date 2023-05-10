package com.example.goodsaccounting.sales_seller.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.dashboard_manager.model.products_and_materials.ProductsAndMaterialsEvent
import com.example.goodsaccounting.dashboard_manager.view.products_and_materials.message_for_ProductsAndMaterialsScreen
import com.example.goodsaccounting.nav.view.LocalAppNavController
import com.example.goodsaccounting.sales_seller.model.SalesSellerEvent
import com.example.goodsaccounting.sales_seller.view_model.SalesSellerVM
import org.orbitmvi.orbit.compose.collectAsState

const val message_for_SalesSellerScreen = "message_for_SalesSellerScreen"

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SalesSellerScreen(
    createSale: () -> Unit,
    editSale : (idSale: String) -> Unit,
) {
    val salesSellerVM by rememberDIAwareViewModel<SalesSellerVM>()
    val state by salesSellerVM.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val appNavController = LocalAppNavController.current
    val message = appNavController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>(message_for_SalesSellerScreen)
    LaunchedEffect(message){
        message?.let {
            scaffoldState.snackbarHostState.showSnackbar(it)
            appNavController.currentBackStackEntry
                ?.savedStateHandle
                ?.set(message_for_SalesSellerScreen, null)
        }
    }
    val refreshState = rememberPullRefreshState(refreshing = state.isRefreshing, onRefresh = { salesSellerVM.onEvent(SalesSellerEvent.Refresh) })
    LaunchedEffect(Unit){
        salesSellerVM.onEvent(SalesSellerEvent.Refresh)
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = createSale) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = stringResource(R.string.your_sales),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(vertical = MaterialTheme.padding.medium2),
                style = MaterialTheme.typography.h5,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(refreshState)
            ){
                ListSaleSellerView(
                    state = state,
                    eventBase = salesSellerVM,
                    editSale = editSale,
                )
                PullRefreshIndicator(
                    state.isRefreshing,
                    refreshState,
                    Modifier.align(Alignment.TopCenter),
                    contentColor = MaterialTheme.colors.primary
                )
            }
        }
    }
}