package com.example.goodsaccounting.nav.model

import com.example.goodsaccounting.R

internal sealed class ManagerUserRouting(route:String) : AppRouting(route) {
    object Dashboard :  ManagerUserRouting("${route}_dashboard"), BottomItemFeature{
        override val label = R.string.home
        override val icon = R.drawable.ic_home
    }
    object Analytics : ManagerUserRouting("${route}_analytics"), BottomItemFeature{
        override val label = R.string.statics
        override val icon = R.drawable.ic_bar_chart
    }
    object Profile : ManagerUserRouting("${route}_profile"), BottomItemFeature {
        override val label = R.string.profile
        override val icon = R.drawable.ic_person
    }
    companion object{
        const val route = "manager_user"
        val listBottomItemFeature = listOf<BottomItemFeature>(Dashboard,Analytics,Profile)
    }
}