package com.example.core.data.data_soure.database

import android.content.SharedPreferences
import com.example.core_data.utils.stringNullable

internal class TokenDatabase(
    sharedPreferences: SharedPreferences
) {
    var accessToken by sharedPreferences.stringNullable()
    var refreshToken by sharedPreferences.stringNullable()
}