package com.example.goodsaccounting.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.goodsaccounting.auth.view.AuthScreen
import com.example.goodsaccounting.common.view.theme.GoodsAccountingTheme
import com.example.goodsaccounting.nav.model.NoneUserRouting
import com.example.goodsaccounting.nav.view.InitNav
import com.example.goodsaccounting.nav.view.noneUserNav
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

internal class MainActivity : ComponentActivity(), DIAware {
    override val di: DI by closestDI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoodsAccountingTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                ) {
                    InitNav()
                }
            }
        }
    }

}
