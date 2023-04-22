package com.example.goodsaccounting.user_administration.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.theme.Black900
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.utils.getStringRole

@Composable
internal fun ListRole(
    currentPage: Int,
    listRole: List<RoleLevel>,
    onClick: (Int) -> Unit
) {
    val stateRowRole = rememberLazyListState()
    LaunchedEffect(currentPage){
        stateRowRole.animateScrollToItem(currentPage)
    }
    LazyRow(
        state = stateRowRole,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.medium1),
        contentPadding = PaddingValues(horizontal = MaterialTheme.padding.medium2),
    ){
        itemsIndexed(listRole){ index, role->
            CartRoleView(role, currentPage == index){
                onClick(index)
            }
        }
    }
}

@Composable
private fun CartRoleView(
    roleLevel: RoleLevel,
    selected: Boolean,
    onClick: ()-> Unit,
) {
    val animatorColorBox by animateColorAsState(
        targetValue = if(selected) MaterialTheme.colors.primary
        else MaterialTheme.colors.surface,
    )
    val animatorColorText by animateColorAsState(
        targetValue = if(selected) Color.White
        else Black900.copy(0.6f),
    )
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .alphaClick(onClick = onClick)
            .background(animatorColorBox)
            .padding(
                horizontal = MaterialTheme.padding.medium2,
                vertical = MaterialTheme.padding.medium1
            )
    ){
        Text(
            text = getStringRole(roleLevel),
            style = MaterialTheme.typography.body2,
            color = animatorColorText,
        )
    }
}