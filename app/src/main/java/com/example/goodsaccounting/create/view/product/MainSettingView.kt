package com.example.goodsaccounting.create.view.product

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create.model.product.CreateProductEvent
import com.example.goodsaccounting.create.model.product.CreateProductState

@Composable
internal fun MainSettingView(
    state: CreateProductState,
    eventBase: EventBase<CreateProductEvent>,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { uriIsNotNull ->
            eventBase.onEvent(
                CreateProductEvent.SelectImage(uriIsNotNull.toString())
            )
        }
    }
    CustomImage(
        image = state.imageUri,
        modifier = Modifier.fillMaxWidth()
            .height(220.dp)
            .clickable { launcher.launch("image/*") }
    )
}