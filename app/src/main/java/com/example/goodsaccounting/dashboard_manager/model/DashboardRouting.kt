package com.example.goodsaccounting.dashboard_manager.model

import androidx.annotation.DrawableRes
import com.example.goodsaccounting.R
import com.example.goodsaccounting.nav.model.AppRouting

internal sealed class DashboardRouting(route: String, @DrawableRes val icon: Int) : AppRouting(route) {
    object Warehouse : DashboardRouting("warehouse", R.drawable.ic_warehouse)
    object ProductsAndMaterials : DashboardRouting("product_and_materials", R.drawable.ic_handyman)
    object WarehouseHistory : DashboardRouting("warehouse_history", R.drawable.ic_history)

    companion object {
        val listItem by lazy { listOf(Warehouse, ProductsAndMaterials, WarehouseHistory) }
    }
}
