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
        val fractionPart = String.format("%02d",  text.toInt())
        "0.$fractionPart".toFloat()
    }
}
fun floatToString(float: Float) : String{
    val list = float.toString().split(".")
    val intPart = if (list.first() == "0") "" else  list.first()
    val fractionPart =  if (list.last().length == 2){
        if (intPart.isEmpty() && list.last().first() == '0')
            list.last().last().toString()
        else
            list.last()
    }else list.last() + "0"
    return intPart + fractionPart
}