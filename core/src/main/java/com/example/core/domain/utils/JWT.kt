package com.example.core.domain.utils

import android.os.Build
import org.json.JSONObject
import java.util.*


internal class JWT(token: String){

    private var chunks = token.split('.')

    operator fun get(item: Int) : String?{
        val text = chunks.getOrNull(item) ?: return null
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getUrlDecoder().decode(text).decodeToString() }
        else { null }
    }

    fun getMap(item: Int) : Map<String, *>? {
        val text = this[item] ?: return null
        return JSONObject(text).toMap()
    }

}