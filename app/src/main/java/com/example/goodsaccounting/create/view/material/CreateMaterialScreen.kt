package com.example.goodsaccounting.create.view.material

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CustomButton
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view_model.rememberDIAwareViewModel
import com.example.goodsaccounting.create.model.material.CreateMaterialEvent
import com.example.goodsaccounting.create.model.material.CreateMaterialSideEffect
import com.example.goodsaccounting.create.view_model.CreateMaterialVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun CreateMaterialScreen(
    exit: ()-> Unit
) {
    val createMaterialVM by rememberDIAwareViewModel<CreateMaterialVM>()
    val state by createMaterialVM.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val res = LocalContext.current.resources
    createMaterialVM.collectSideEffect{sideEffect ->
        when(sideEffect){
            CreateMaterialSideEffect.ProductCreated -> exit()
            is CreateMaterialSideEffect.ShowMessage -> {
                scaffoldState.snackbarHostState
                    .showSnackbar(res.getString(sideEffect.message))
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MainSettingView(
                state = state,
                eventBase = createMaterialVM,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (state.isCrating){
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = MaterialTheme.padding.medium2)
                )
            }else{
                CustomButton(
                    label = stringResource(R.string.create),
                    modifier = Modifier.padding(MaterialTheme.padding.medium1)
                ) {
                    createMaterialVM.onEvent(CreateMaterialEvent.Create)
                }
            }
        }
    }

}