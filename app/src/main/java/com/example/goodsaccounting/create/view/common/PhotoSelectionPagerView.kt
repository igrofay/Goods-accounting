package com.example.goodsaccounting.create.view.common

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.common.view.click.scaleClick
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PhotoSelectionPagerView(
    listImageUri: List<String>,
    add: (position: Int, uri: String)->Unit,
    remove: (position: Int) -> Unit,
) {
    val pagerState = rememberPagerState()
    val localCoroutine = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { uriIsNotNull ->
            add(pagerState.currentPage,uriIsNotNull.toString())
        }
    }

    Column {
        HorizontalPager(
            pageCount = listImageUri.size.inc(),
            state = pagerState,
        ) { index ->
            val modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clickable { launcher.launch("image/*") }
            if (index == listImageUri.size) {
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
                Box {
                    CustomImage(
                        image = listImageUri[index],
                        modifier = modifier
                    )
                    IconButton(
                        onClick = {
                            remove(index)
                        },
                        modifier = Modifier
                            .padding(
                                top = MaterialTheme.padding.small2,
                                end = MaterialTheme.padding.small2
                            )
                            .align(Alignment.TopEnd)
                            .background(MaterialTheme.colors.surface, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
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
            items(listImageUri.size.inc()) { index ->
                val animateColor by animateColorAsState(
                    targetValue = if (index == pagerState.currentPage)
                        MaterialTheme.colors.secondary
                    else MaterialTheme.colors.onSurface.copy(0.3f)
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
    }
}