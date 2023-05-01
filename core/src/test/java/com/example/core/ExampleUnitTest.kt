package com.example.core

import com.example.core.domain.model.product.AmountOfMaterialModel
import com.example.core.domain.model.product.Currency
import com.example.core.domain.model.product.MaterialModel
import com.example.core.domain.model.product.Measurements
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println(
            Json.decodeFromString<A>(
                Json.encodeToString(
                    AmountOfMaterialModel(
                        10f,
                        object : MaterialModel{
                            override val id = ""
                            override val name = ""
                            override val measurement = Measurements.Piece
                            override val imageUrl = null

                        }
                    )
                )
            )
        )
    }
    @Serializable
    data class A(val c: Currency)
}