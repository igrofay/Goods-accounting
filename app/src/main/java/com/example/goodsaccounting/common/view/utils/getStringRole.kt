package com.example.goodsaccounting.common.view.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.core.domain.model.user.RoleLevel
import com.example.goodsaccounting.R

@Composable
internal fun getStringRole(roleLevel: RoleLevel) : String {
    val res = LocalContext.current.resources
    return remember(roleLevel) {
        when(roleLevel){
            RoleLevel.None -> res.getString(R.string.under_consideration)
            RoleLevel.Seller -> res.getString(R.string.seller)
            RoleLevel.Manager ->  res.getString(R.string.manager)
            RoleLevel.Administrator -> res.getString(R.string.administrator)
        }
    }
}