package com.example.goodsaccounting.user_administration.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.model.user.UserModel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CustomTextButton
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.theme.Black900
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.common.view.utils.getStringRole
import com.example.goodsaccounting.common.view.visual_transformation.PhonedVisualTransformation
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.user_administration.model.UserAdministrationEvent
import com.skydoves.landscapist.glide.GlideImage

@Composable
internal fun UserInfoView(
    userModel: UserModel,
    newUserRole: RoleLevel?,
    eventBase: EventBase<UserAdministrationEvent>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp)
            .background(
                MaterialTheme.colors.background,
                MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .padding(top = 25.dp + MaterialTheme.padding.medium1)
                .padding(
                    horizontal = MaterialTheme.padding.medium2,
                    vertical = MaterialTheme.padding.medium1
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(MaterialTheme.padding.medium1)
        ) {
            Text(
                text = "${userModel.lastname} ${userModel.firstname} ${userModel.patronymic}",
                maxLines = 2,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small2)
            ) {
                Text(
                    text = userModel.email,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.weight(1f),
                    maxLines = 1
                )
                Text(
                    text = PhonedVisualTransformation
                        .transformText(text = userModel.phone),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    textAlign = TextAlign.End,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.select_status),
                    style = MaterialTheme.typography.body2,
                )
                var expanded by remember { mutableStateOf(false) }
                Box {
                    Row(
                        modifier = Modifier.alphaClick { expanded = !expanded },
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = getStringRole(roleLevel = newUserRole ?: userModel.role),
                            style = MaterialTheme.typography.body2,
                        )
                        val animRotate by animateFloatAsState(
                            targetValue = if (expanded) 180f else 0f
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(animRotate),
                            tint = MaterialTheme.colors.onBackground,
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                    ) {
                        RoleLevel.values().forEach { role ->
                            DropdownMenuItem(onClick = {
                                eventBase.onEvent(UserAdministrationEvent.SelectedNewUserRole(role))
                                expanded = false
                            }) {
                                Text(
                                    text = getStringRole(roleLevel = role),
                                    style = MaterialTheme.typography.body2,
                                    color = MaterialTheme.colors.textColor
                                )
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomTextButton(
                    label = stringResource(id = R.string.close),
                    color = MaterialTheme.colors.error,
                ) {
                    eventBase.onEvent(UserAdministrationEvent.CloseChangeUserRole)
                }
                CustomTextButton(
                    label = stringResource(id = R.string.save),
                    color = MaterialTheme.colors.secondary,
                ) {
                    eventBase.onEvent(UserAdministrationEvent.SaveNewUserRole)
                }
            }
        }
        GlideImage(
            imageModel = { userModel.imageUrl },
            modifier = Modifier
                .offset(y = (-50).dp)
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFD9D9D9))
                .border(1.dp, MaterialTheme.colors.onBackground.copy(.2f), CircleShape),
            failure = {
                Icon(
                    painter = painterResource(R.drawable.ic_person),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                )
            }
        )
    }
}