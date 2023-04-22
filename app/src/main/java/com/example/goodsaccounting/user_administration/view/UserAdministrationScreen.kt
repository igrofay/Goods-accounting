package com.example.goodsaccounting.user_administration.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.user_administration.model.UserAdministrationEvent
import com.example.goodsaccounting.user_administration.model.UserAdministrationSideEffect
import com.example.goodsaccounting.user_administration.view_model.UserAdministrationVM
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
internal fun UserAdministrationScreen() {
    val userAdministrationVM by rememberDIAwareViewModel<UserAdministrationVM>()
    val pagerState = rememberPagerState()

    val state by userAdministrationVM.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        state.isRefreshing,
        { userAdministrationVM.onEvent(UserAdministrationEvent.Refresh) }
    )
    val localCoroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .padding(top = MaterialTheme.padding.small2),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.medium2)
        ) {
            Text(
                text = stringResource(id = R.string.users),
                modifier = Modifier.align(CenterHorizontally),
                style = MaterialTheme.typography.h5,
            )
            ListRole(
                currentPage = pagerState.currentPage,
                listRole = state.users.keys.toList(),
            ) {index->
                localCoroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
            UserPager(users = state.users, pagerState = pagerState, userAdministrationVM)
        }
        PullRefreshIndicator(
            state.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter),
            contentColor = MaterialTheme.colors.primary
        )
        state.changeUserRole?.let {userModel ->
            Dialog(
                onDismissRequest = { userAdministrationVM
                    .onEvent(UserAdministrationEvent.CloseChangeUserRole)
                }
            ) {
                UserInfoView(
                    userModel = userModel,
                    newUserRole = state.newUserRole,
                    eventBase = userAdministrationVM,
                )

            }
        }
    }
}