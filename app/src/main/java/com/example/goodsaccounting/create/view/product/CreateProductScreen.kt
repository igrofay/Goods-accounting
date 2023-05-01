package com.example.goodsaccounting.create.view.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.create.view_model.CreateProductVM
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun CreateProductScreen() {
    val createProductVM by rememberDIAwareViewModel<CreateProductVM>()
    val state by createProductVM.collectAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            MainSettingView(
                state = state,
                eventBase = createProductVM,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}