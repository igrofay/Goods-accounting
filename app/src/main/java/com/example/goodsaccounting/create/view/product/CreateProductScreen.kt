package com.example.goodsaccounting.create.view.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CustomButton
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.create.model.material.CreateMaterialEvent
import com.example.goodsaccounting.create.model.product.CreateProductEvent
import com.example.goodsaccounting.create.view_model.CreateProductVM
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun CreateProductScreen() {
    val createProductVM by rememberDIAwareViewModel<CreateProductVM>()
    val state by createProductVM.collectAsState()
    val scrollState = rememberScrollState()
    Column {
        Scaffold(
            floatingActionButton = {
                if (scrollState.canScrollForward || !scrollState.canScrollBackward)
                    FloatingActionButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                        )
                    }
            },
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(scrollState)
                    .weight(1f)
            ) {
                MainSettingView(
                    state = state,
                    eventBase = createProductVM,
                )
                MaterialsView(
                    state = state,
                    eventBase = createProductVM,
                )
            }
        }
        if (state.isCreating) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = MaterialTheme.padding.medium2)
            )
        } else {
            CustomButton(
                label = stringResource(R.string.create),
                modifier = Modifier.padding(MaterialTheme.padding.medium1)
            ) {
                createProductVM.onEvent(CreateProductEvent.Create)
            }
        }
    }

}