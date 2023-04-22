package com.example.goodsaccounting.profile.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.state_view.LoadView
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.utils.getStringRole
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.profile.model.ProfileEvent
import com.skydoves.landscapist.glide.GlideImage

@Composable
internal fun HeaderView(
    image: String?,
    roleLevel: RoleLevel,
    eventBase: EventBase<ProfileEvent>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally,
    ){
        Text(
            text = stringResource(id = R.string.your_profile),
            style = MaterialTheme.typography.h5,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.padding.large1))
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { uriIsNotNull ->
                eventBase.onEvent(ProfileEvent.SelectImage(uriIsNotNull.toString()))
            }
        }
        GlideImage(
            imageModel = { image },
            modifier = Modifier
                .alphaClick {
                    launcher.launch("image/*")
                }
                .align(CenterHorizontally)
                .height(120.dp)
                .width(170.dp)
                .clip(CircleShape)
                .background(Color(0xFFD9D9D9)),
            loading = { LoadView() },
            failure = {
                Icon(
                    painter = painterResource( R.drawable.ic_person),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Center)
                        .size(50.dp)
                )
            }
        )
        Spacer(modifier = Modifier.height(MaterialTheme.padding.medium1))
        Text(
            text = "${stringResource(id = R.string.your_status)} ${getStringRole(roleLevel)}",
            style = MaterialTheme.typography.subtitle1
        )
    }
}