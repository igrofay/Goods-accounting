package com.example.goodsaccounting

import com.example.goodsaccounting.create_or_edit.model.utils.floatToString
import com.example.goodsaccounting.create_or_edit.model.utils.stringToFloat
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val t = stringToFloat("1010")
        println(t)
        val s = floatToString(t)
        println(s)
        println(stringToFloat(s))
    }
}