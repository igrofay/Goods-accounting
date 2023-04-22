package com.example.core

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.domain.model.user.RoleLevel
import com.example.core.domain.utils.JWT

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    // Context of the app under test.
   // val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    ///assertEquals("com.example.core.test", appContext.packageName)


    @Test
    fun useAppContext() {
        val role = JWT(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOiJiMWZhOGNmNC1lOTFiLTQ4ZGMtYjQxZC1jNjgwNGUwNThmMzIiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJBZG1pbmlzdHJhdG9yIiwiZXhwIjoxNjgxMTk3OTQ3fQ.n49DcrcnmWIKAqDr-IdAC1UiZ2OA1oZfT9hTDAtaTds"
        ).getMap(1)?.get("http://schemas.microsoft.com/ws/2008/06/identity/claims/role")
        role ?: return
        println(role::class)
    }
}