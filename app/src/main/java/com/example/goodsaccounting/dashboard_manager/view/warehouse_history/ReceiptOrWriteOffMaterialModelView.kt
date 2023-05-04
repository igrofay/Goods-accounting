package com.example.goodsaccounting.dashboard_manager.view.warehouse_history

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.core.domain.model.warehouse.ReceiptOrWriteOffMaterialModel

@Composable
internal fun ReceiptOrWriteOffMaterialModelView(
    list: List<ReceiptOrWriteOffMaterialModel>
) {
    LazyRow{
        items(list){

        }
    }
}