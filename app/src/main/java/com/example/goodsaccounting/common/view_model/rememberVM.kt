package com.example.goodsaccounting.common.view_model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.compose.localDI
import kotlin.reflect.full.starProjectedType

@Composable
inline fun <reified VM> rememberDIAwareViewModel() where VM: ViewModel, VM: DIAware = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current ?: error("")
    val localSavedStateRegistryOwner = LocalSavedStateRegistryOwner.current
    val bundle = if (viewModelStoreOwner is NavBackStackEntry) viewModelStoreOwner.arguments else null
    val constructor = VM::class.constructors.first()
    remember {
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { viewModelStoreOwner.viewModelStore },
            factoryProducer = {
                object : AbstractSavedStateViewModelFactory(localSavedStateRegistryOwner, bundle){
                    override fun <T : ViewModel> create(
                        key: String,
                        modelClass: Class<T>,
                        handle: SavedStateHandle
                    ): T{
                        val arg = constructor.parameters.associateWith {
                            when(it.type){
                                (DI::class.starProjectedType)-> di
                                (SavedStateHandle::class.starProjectedType)-> handle
                                else-> error("Not Found")
                            }
                        }
                        return constructor.callBy(arg) as T
                    }

                }
            }
        )
    }
}