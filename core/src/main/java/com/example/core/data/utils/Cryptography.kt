package com.example.core.data.utils

import java.security.MessageDigest

internal fun String.toSha256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    val byteArray = md.digest(this.toByteArray())
    return byteArray.joinToString("") { "%02x".format(it) }
}

internal class VernamCipher {
    fun encrypt(text: String, key: String) : String{
        var answer = ""
        for (item in text.indices){
            answer += (key[item].code xor text[item].code).toChar()
        }
        return answer
    }

    fun decipher(text: String, key: String) : String {
        var answer = ""
        for (item in text.indices){
            answer += (key[item].code xor text[item].code).toChar()
        }
        return answer
    }

    fun generateKey(size: Int, offset: Int, a: Int, c: Int, mod: Int) : String{
        var answer = ""
        for (i in offset until  (offset + size)){
            answer += ((a * i + c) % mod).toChar()
        }
        return answer
    }
}