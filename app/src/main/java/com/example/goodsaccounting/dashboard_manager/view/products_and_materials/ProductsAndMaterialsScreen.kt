package com.example.goodsaccounting.dashboard_manager.view.products_and_materials

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.dashboard_manager.model.LocalDashboardSetting
import com.example.goodsaccounting.dashboard_manager.view_model.ProductsAndMaterialsVM
import com.example.goodsaccounting.nav.model.ManagerUserRouting
import com.example.goodsaccounting.nav.view.LocalAppNavController
import com.example.goodsaccounting.nav.view.navWithClearStack
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun ProductsAndMaterialsScreen() {
    val productsAndMaterialsState by rememberDIAwareViewModel<ProductsAndMaterialsVM>()
    val state by productsAndMaterialsState.collectAsState()
    val pagerState = rememberPagerState()
    val localCoroutine = rememberCoroutineScope()
    val dashboardSetting = LocalDashboardSetting.current
    LaunchedEffect(dashboardSetting) {
        dashboardSetting.setLabel(R.string.products_and_materials)
    }
    Column(
        modifier = Modifier
            .padding(top = MaterialTheme.padding.extraLarge1 * 1.75f),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small2)
    ) {
        TapProductsAndMaterial(
            currentPage = pagerState.currentPage,
            onClick = { page ->
                localCoroutine.launch {
                    pagerState.animateScrollToPage(page)
                }
            }
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val appNavController = LocalAppNavController.current
            ProductsAndMaterialsPager(
                state = state,
                pagerState = pagerState
            )
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
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = MaterialTheme.padding.medium1,
                        end = MaterialTheme.padding.medium1,
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }

    }
}