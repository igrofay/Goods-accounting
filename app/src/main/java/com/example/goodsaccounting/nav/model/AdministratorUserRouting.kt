package com.example.goodsaccounting.nav.model

import com.example.goodsaccounting.R

internal sealed class AdministratorUserRouting(route: String) : AppRouting(route) {
    object Profile : AdministratorUserRouting("${route}_profile"), BottomItemFeature {
        override val label = R.string.profile
        override val icon = R.drawable.ic_person
    }
    object ListUser : AdministratorUserRouting("${route}_list_user"), BottomItemFeature{
        override val label = R.string.users
        override val icon = R.drawable.ic_groups
    }
    companion object{
        const val route = "administrator_user"
        val listBottomItemFeature = listOf<BottomItemFeature>(ListUser, Profile)
    }
}