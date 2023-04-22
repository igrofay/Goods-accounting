package com.example.goodsaccounting.user_administration.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.model.user.UserModel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.visual_transformation.PhonedVisualTransformation
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.user_administration.model.UserAdministrationEvent
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun UserPager(
    users: Map<RoleLevel, List<UserModel>>,
    pagerState: PagerState,
    eventBase: EventBase<UserAdministrationEvent>,
) {
    HorizontalPager(
        pageCount = users.size,
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
    ) { page ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = MaterialTheme.padding.medium2,
                vertical = MaterialTheme.padding.small2,
            ),
            verticalArrangement = Arrangement
                .spacedBy(MaterialTheme.padding.medium1)
        ) {
            items(users.values.toList().getOrNull(page) ?: listOf()) { userModel ->
                UserItem(userModel) {
                    eventBase.onEvent(
                        UserAdministrationEvent
                            .OpenUserForChangeRole(userModel)
                    )
                }
            }
        }
    }
}

@Composable
private fun UserItem(
    userModel: UserModel,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .scaleClick(onClick = onClick)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(MaterialTheme.colors.background, MaterialTheme.shapes.medium)
            .border(
                1.dp,
                MaterialTheme.colors.onBackground.copy(0.2f),
                MaterialTheme.shapes.medium
            )
            .padding(MaterialTheme.padding.medium1),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.medium1),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            imageModel = { userModel.imageUrl },
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFFD9D9D9)),
            failure = {
                Icon(
                    painter = painterResource(R.drawable.ic_person),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(30.dp)
                )
            }
        )
        Column(
            verticalArrangement = Arrangement
                .spacedBy(MaterialTheme.padding.small2)
        ) {
            Text(
                text = "${userModel.lastname} ${userModel.firstname} ${userModel.patronymic}",
                maxLines = 2,
                style = MaterialTheme.typography.h6,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small2)
            ){
                Text(
                    text = userModel.email,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.weight(1f),
                    maxLines = 1
                )
                BasicTextField(
                    value = userModel.phone,
                    onValueChange = {},
                    textStyle = MaterialTheme.typography
                        .caption.copy(
                            color = MaterialTheme.colors.onBackground,
                            textAlign = TextAlign.End
                        ),
                    readOnly = true,
                    enabled = false,
                    visualTransformation = PhonedVisualTransformation(
                        PhonedVisualTransformation.russianMask,
                        PhonedVisualTransformation.maskNumber,
                    ),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                )
            }
        }
    }

}