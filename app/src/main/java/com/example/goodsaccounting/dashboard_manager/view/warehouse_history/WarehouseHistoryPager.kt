package com.example.goodsaccounting.dashboard_manager.view.warehouse_history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import com.example.goodsaccounting.dashboard_manager.model.warehouse_history.WarehouseHistoryState

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun WarehouseHistoryPager(
    pagerState: PagerState,
    state: WarehouseHistoryState,
) {
    HorizontalPager(
        pageCount = 3,
        state = pagerState,
    ) {page->
        when(page){
            // Sales
            0-> {}
            // Receipt
            1->ReceiptOrWriteOffMaterialModelView(
                list = state.receiptMaterial
            )
            // Write off
            2-> ReceiptOrWriteOffMaterialModelView(
                list = state.writeOffMaterial
            )
        }
    }
}