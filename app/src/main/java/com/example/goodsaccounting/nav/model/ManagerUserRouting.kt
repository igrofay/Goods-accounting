package com.example.goodsaccounting.nav.model

import com.example.goodsaccounting.R

internal sealed class ManagerUserRouting(route:String) : AppRouting(route) {

    sealed class ComponentsWitBottomBar(route: String): ManagerUserRouting(route), BottomItemFeature{
        object Dashboard :  ComponentsWitBottomBar("${route}_dashboard"){
            override val label = R.string.home
            override val icon = R.drawable.ic_home
        }
        object Analytics : ComponentsWitBottomBar("${route}_analytics") {
            override val label = R.string.statics
            override val icon = R.drawable.ic_bar_chart
        }
        object Profile : ComponentsWitBottomBar("${route}_profile"){
            override val label = R.string.profile
            override val icon = R.drawable.ic_person
        }
        companion object{
            const val route = "${ManagerUserRouting.route}_components_wit_bottom_bar"
            val listBottomItemFeature = listOf<BottomItemFeature>(Dashboard,Analytics,Profile)
        }
    }
    object CreateOrEditMaterial : ManagerUserRouting("${route}_create_or_edit_material"){
        const val arg1 = "idMaterial"
        fun getAllRoute() = "${route}?$arg1={$arg1}"
        fun getAllRoute(idMaterial: String?) = idMaterial?.let {
            "${route}?$arg1=$it"
        } ?: route
    }

    object CrateOrEditProduct : ManagerUserRouting("${route}_create_product"){
        const val arg1 = "idProduct"
        fun getAllRoute() = "$route?${arg1}={${arg1}}"

        fun getAllRoute(idProduct: String?) = idProduct?.let {
            "$route?${arg1}=$it"
        } ?: route
    }

    object CreateReceiptOrWriteOfMaterial : ManagerUserRouting("${route}_create_receipt_material"){
        const val arg1= "isReceipt"
        fun allRoute() = "${route}/{$arg1}"
        fun allRoute(isReceipt: Boolean) = "${route}/$isReceipt"
    }
    companion object{
        const val route = "manager_user"
    }
}