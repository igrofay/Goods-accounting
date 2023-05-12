package com.example.goodsaccounting.dashboard_manager.view.warehouse

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.core.domain.model.filter.FilterListMaterial
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.dashboard_manager.model.dashboard.LocalDashboardSetting
import com.example.goodsaccounting.dashboard_manager.model.warehouse.WarehouseEvent
import com.example.goodsaccounting.dashboard_manager.view_model.WarehouseVM
import org.orbitmvi.orbit.compose.collectAsState

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun WarehouseScreen() {
    val dashboardSetting = LocalDashboardSetting.current
    LaunchedEffect(dashboardSetting){
        dashboardSetting.setLabel(R.string.warehouse)
    }
    val warehouseVM by rememberDIAwareViewModel<WarehouseVM>()
    val state by warehouseVM.collectAsState()
    val refreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { warehouseVM.onEvent(WarehouseEvent.Refresh) }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            var expanded by remember {
                mutableStateOf(false)
            }
            var width by remember {
                mutableStateOf(0)
            }
            Box(
                modifier = Modifier
                    .scaleClick { expanded = true }
                    .padding(
                        horizontal = MaterialTheme.padding.medium2,
                        vertical = MaterialTheme.padding.medium1
                    )
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        width = it.size.width
                    }
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colors.surface, MaterialTheme.shapes.medium)
            ){
                val animateRotate by animateFloatAsState(
                    targetValue = if (expanded) 180f else 0f
                )
                Text(
                    text = when(state.filterListMaterial){
                        FilterListMaterial.Alphabetically -> stringResource(R.string.alphabetically)
                        FilterListMaterial.InCount -> stringResource(R.string.in_count)
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(MaterialTheme.padding.medium1),
                    style = MaterialTheme.typography.subtitle1,
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = MaterialTheme.padding.medium1)
                        .rotate(animateRotate)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current){width.toDp()})
                ) {
                    FilterListMaterial.values().forEach {
                        DropdownMenuItem(onClick = {
                            warehouseVM.onEvent(WarehouseEvent.SelectFilter(it))
                            expanded = false
                        }) {
                            Text(
                                text = when(it){
                                    FilterListMaterial.Alphabetically -> stringResource(R.string.alphabetically)
                                    FilterListMaterial.InCount -> stringResource(R.string.in_count)
                                },
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            ListAmountOfMaterialView(
                list = state.listAmountOfMaterial
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