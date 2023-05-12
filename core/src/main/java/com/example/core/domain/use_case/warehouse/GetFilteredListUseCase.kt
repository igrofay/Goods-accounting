package com.example.core.domain.use_case.warehouse

import com.example.core.domain.model.filter.FilterListMaterial
import com.example.core.domain.model.product.Measurements
import com.example.core.domain.repos.WarehouseRepos

class GetFilteredListUseCase(
    private val warehouseRepos: WarehouseRepos,
) {
    suspend fun execute(
        filterListMaterial: FilterListMaterial
    ) = runCatching {
        when (filterListMaterial) {
            FilterListMaterial.Alphabetically -> warehouseRepos
                .getListAmountOfMaterial().sortedBy { amountOfMaterial ->
                    amountOfMaterial.materialModel.name
                }
            FilterListMaterial.InCount ->warehouseRepos
                .getListAmountOfMaterial().sortedBy { amountOfMaterial ->
                    transformationValue(amountOfMaterial.amount, amountOfMaterial.materialModel.measurement)
                }
        }
    }
    private fun transformationValue(value: Float,measurement: Measurements): Float{
        return when(measurement){
            Measurements.Piece -> value
            Measurements.Kilogram -> value
            Measurements.Gram -> value / 1_000
            Measurements.Liter -> value
            Measurements.Milliliter -> value / 1_000
            Measurements.Meter -> value
            Measurements.Centimeter -> value / 100
            Measurements.Other -> value
        }
    }
}