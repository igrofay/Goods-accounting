package com.example.goodsaccounting.common.view_model

import com.example.goodsaccounting.common.model.UIEvent


internal interface EventBase<T: UIEvent> {
    fun onEvent(event: T)
}