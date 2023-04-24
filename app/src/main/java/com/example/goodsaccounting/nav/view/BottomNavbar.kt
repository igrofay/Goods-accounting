package com.example.goodsaccounting.nav.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.nav.model.BottomItemFeature

@Composable
internal fun BottomNavbar(
    listItems: List<BottomItemFeature>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.padding.medium1),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            for (item in listItems) {
                val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colors.primary.copy(0.2f)
                    else Color.Transparent,
                )
                val contentColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colors.primary
                    else MaterialTheme.colors.textColor

                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .background(
                            backgroundColor,
                            CircleShape,
                        )
                        .padding(
                            vertical = MaterialTheme.padding.small2,
                            horizontal = MaterialTheme.padding.small2 * 1.5f
                        ),
                    horizontalArrangement = Arrangement
                        .spacedBy(MaterialTheme.padding.small1)
                ) {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = contentColor
                    )
                    AnimatedVisibility(visible = isSelected) {
                        Text(
                            text = stringResource(item.label),
                            color = contentColor,
                            style = MaterialTheme.typography.body2,
                        )
                    }
                }
            }
        }
    }

}