package com.example.core.di

import com.example.core.domain.use_case.admin.GetUsersUseCase
import com.example.core.domain.use_case.analytics.GetSellerIncomeUseCase
import com.example.core.domain.use_case.auth.RestoreSessionUseCase
import com.example.core.domain.use_case.auth.SignInUseCase
import com.example.core.domain.use_case.auth.SignUpUseCase
import com.example.core.domain.use_case.create.CreateMaterialUseCase
import com.example.core.domain.use_case.create.CreateProductUseCase
import com.example.core.domain.use_case.create.CreateReceiptOrWriteOffMaterialUseCase
import com.example.core.domain.use_case.create.CreateSaleSellerUseCase
import com.example.core.domain.use_case.edit.EditSaleUseCase
import com.example.core.domain.use_case.product.GetMapIdToMaterialModelUseCase
import com.example.core.domain.use_case.product.GetMapIdToProductModelUseCase
import com.example.core.domain.use_case.seller.GetSaleUseCase
import com.example.core.domain.use_case.seller.GetSalesUseCase
import com.example.core.domain.use_case.user.GetUserFlowUseCase
import com.example.core.domain.use_case.user.UpdateUserDataUseCase
import com.example.core.domain.use_case.warehouse.GetFilteredListUseCase
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.new

internal val useCaseModule by DI.Module{
    // auth
    bindSingleton { new(::RestoreSessionUseCase) }
    bindProvider { new(::SignInUseCase) }
    bindProvider { new(::SignUpUseCase) }
    // user
    bindProvider { new(::UpdateUserDataUseCase) }
    bindProvider { new(::GetUserFlowUseCase) }
    // admin
    bindProvider { new(::GetUsersUseCase) }
    // manager
    bindProvider { new(::CreateMaterialUseCase) }
    bindProvider { new(::CreateProductUseCase) }
    bindProvider { new(::CreateReceiptOrWriteOffMaterialUseCase) }
    bindProvider { new(::GetFilteredListUseCase) }
    // Product
    bindProvider { new(::GetMapIdToMaterialModelUseCase) }
    bindProvider { new(::GetMapIdToProductModelUseCase) }
    // Seller
    bindProvider { new(::CreateSaleSellerUseCase) }
    bindProvider { new(::GetSellerIncomeUseCase) }
    bindProvider { new(::GetSalesUseCase) }
    bindProvider { new(::EditSaleUseCase) }
    bindProvider { new(::GetSaleUseCase) }
}