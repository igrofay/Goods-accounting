package com.example.goodsaccounting.create_or_edit.model.utils

fun stringToFloat(text: String): Float {
    if (text.isBlank()) return 0f
    return if (text.length > 2) {
        val intPart = text
            .dropLast(2)
        val fractionPart = text
            .takeLast(2)
        "$intPart.$fractionPart".toFloat()
    } else {
        "0.$text".toFloat()
    }
}