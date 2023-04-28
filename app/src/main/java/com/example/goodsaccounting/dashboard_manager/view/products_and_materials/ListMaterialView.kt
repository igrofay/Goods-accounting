package com.example.goodsaccounting.dashboard_manager.view.products_and_materials

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.domain.model.product.MaterialModel

@Composable
internal fun ListMaterialView(
    listMaterial: List<MaterialModel>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        modifier = Modifier
            .fillMaxSize()
    ){
        items(listMaterial){

        }
    }
}