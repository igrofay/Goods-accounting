package com.example.core.domain.model.error

sealed class SaleError : AppError(){
    object ThereAreFewerMaterialsInStock : SaleError()
}

