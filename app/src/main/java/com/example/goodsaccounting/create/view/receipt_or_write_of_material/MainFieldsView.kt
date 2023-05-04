package com.example.goodsaccounting.create.view.receipt_or_write_of_material

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.goodsaccounting.common.view.edit_text.EditText
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialEvent
import com.example.goodsaccounting.create.model.receipt_or_write_of_material.CreateReceiptOrWriteOfMaterialState
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun MainFieldsView(
    state: CreateReceiptOrWriteOfMaterialState,
    eventBase: EventBase<CreateReceiptOrWriteOfMaterialEvent>
) {
    val pagerState = rememberPagerState()
    val localCoroutine = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { uriIsNotNull ->
            eventBase.onEvent(
                CreateReceiptOrWriteOfMaterialEvent.SelectImage(
                    uriIsNotNull.toString(),
                    pagerState.currentPage
                )
            )
        }
    }
    HorizontalPager(
        pageCount = state.listImageUri.size.inc(),
        state = pagerState,
    ) {
        val modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable { launcher.launch("image/*") }
        if (it == state.listImageUri.size) {
            Box(
                modifier = modifier
                    .background(MaterialTheme.colors.onBackground.copy(0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(34.dp)
                )
            }
        } else {
            CustomImage(
                image = state.listImageUri[it],
                modifier = modifier
            )
        }
    }
    LazyRow(
        horizontalArrangement = Arrangement
            .spacedBy(MaterialTheme.padding.small2, Alignment.CenterHorizontally),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = MaterialTheme.padding.medium1,
            ),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.padding.medium2
        )
    ) {
        items(state.listImageUri.size.inc()) { index ->
            val animateColor by animateColorAsState(
                targetValue = if (index == pagerState.currentPage)
                    MaterialTheme.colors.secondary
                else MaterialTheme.colors.onSurface
            )
            Spacer(
                modifier = Modifier
                    .scaleClick {
                        localCoroutine.launch { pagerState.animateScrollToPage(index) }
                    }
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(animateColor, CircleShape)
            )
        }
    }
    Spacer(modifier = Modifier.height(MaterialTheme.padding.medium2))
    EditText(
        value = state.name,
        onChange = {
            eventBase.onEvent(CreateReceiptOrWriteOfMaterialEvent.InputName(it))
        },
        modifier = Modifier.padding(
            horizontal = MaterialTheme.padding.medium2,
        ),
        hint = if (state.isReceipt)
            stringResource(R.string.name_of_receipt)
        else stringResource(R.string.name_of_write_off),
        isError = state.isErrorName,
    )
}