package com.example.goodsaccounting.dashboard_manager.view.products_and_materials

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.goodsaccounting.dashboard_manager.model.products_and_materials.ProductsAndMaterialsState

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ProductsAndMaterialsPager(
    state: ProductsAndMaterialsState,
    pagerState: PagerState,
) {
    HorizontalPager(
        pageCount = 2,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) {page->
        // Products
        if (page == 0){
            ListProductView(state.listProductModel)
        }
        // Materials
        else if(page == 1){
            ListMaterialView(state.listMaterialModel)
        }
    }
}