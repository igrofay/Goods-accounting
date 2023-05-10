package com.example.goodsaccounting.common.view_model

import androidx.lifecycle.ViewModel
import com.example.goodsaccounting.common.model.mvi.UIEvent
import com.example.goodsaccounting.common.model.mvi.UISideEffect
import com.example.goodsaccounting.common.model.mvi.UIState
import org.orbitmvi.orbit.ContainerHost

internal abstract class AppVM <S: UIState,SF: UISideEffect, E: UIEvent>
    : ViewModel(), ContainerHost<S, SF>, EventBase<E>{
        protected abstract fun onError(error: Throwable)
}