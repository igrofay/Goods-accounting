package com.example.goodsaccounting.dashboard_manager.view.dashbord

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.dashboard_manager.model.dashboard.DashboardRouting

@Composable
internal fun ColumnNavbarView(
    dashboardNavController: NavController,
) {
    val colorDiver = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    val navBackStackEntry by dashboardNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var positionCurrentIconInY by remember {
        mutableStateOf(0f)
    }
    val animPosition by animateFloatAsState(
        targetValue = positionCurrentIconInY
    )
    Row {
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = MaterialTheme.padding.medium1)
                    .padding(top = MaterialTheme.padding.extraLarge1 * 2),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.medium2),
            ) {
                for (item in DashboardRouting.listItem) {
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.route == item.route } == true
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .alphaClick {
                                dashboardNavController.navigate(item.route){
                                    popUpTo(dashboardNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            .size(32.dp)
                            .onGloballyPositioned { coordinates ->
                                if (isSelected) {
                                    positionCurrentIconInY = coordinates.positionInRoot().y
                                }
                            }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(
                        // height clamp 40 dp to align them in the center
                        // use height icon 32 dp => (40 - 32) / 2 = 4 dp
                        y = with(LocalDensity.current) { animPosition.toDp() } - 4.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(
                    modifier = Modifier
                        .height(40.dp)
                        .width(MaterialTheme.padding.small1)
                        .background(
                            MaterialTheme.colors.primary,
                            MaterialTheme.shapes.large.copy(
                                topStart = ZeroCornerSize,
                                bottomStart = ZeroCornerSize
                            )
                        )
                )
                Spacer(
                    modifier = Modifier
                        .height(40.dp)
                        .width(MaterialTheme.padding.small1)
                        .background(
                            MaterialTheme.colors.primary,
                            MaterialTheme.shapes.large.copy(
                                topEnd = ZeroCornerSize,
                                bottomEnd = ZeroCornerSize
                            )
                        )
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(colorDiver)
        )
    }
}