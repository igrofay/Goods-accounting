package com.example.goodsaccounting.common.view.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.goodsaccounting.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
internal fun CustomImage(
    image: String?,
    modifier: Modifier,
) {
    val color = MaterialTheme.colors.onBackground.copy(0.2f)
    var backgroundImage by remember { mutableStateOf(color) }
    GlideImage(
        imageModel = { image },
        modifier = modifier
            .background(backgroundImage),
        failure = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_image),
                    contentDescription = null,
                )
            }
        },
        imageOptions = ImageOptions(
            alignment = Alignment.Center,
            contentScale = ContentScale.FillHeight
        ),
        success = { success ->
            LaunchedEffect(success.imageBitmap) {
                backgroundImage = Color(
                    success.imageBitmap!!
                        .asAndroidBitmap().getPixel(0, 0)
                )
            }
            Image(
                bitmap = success.imageBitmap!!,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
        },
        loading = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
    )
}