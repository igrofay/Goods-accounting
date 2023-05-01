package com.example.core.di

import android.util.Log
import com.example.core.domain.repos.AppRepos
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bindConstant
import org.kodein.di.bindSingleton
import org.kodein.di.instance

const val url = "serverUrl"
const val baseClient = "TAG_baseClient"
const val authorizedClient = "TAG_authorizedClient"

internal val networkMode by DI.Module{
    bindConstant(tag = url){ "http://192.168.100.95:5121" }
    bindSingleton(tag = baseClient) { baseClient(instance(url)) }
    bindSingleton(tag = authorizedClient){ authorizedClient(instance(url),instance()) }
}

private fun baseClient(serverUrl: String) : HttpClient {
    val client = HttpClient(Android){
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
        defaultRequest {
            url(serverUrl)
        }
        expectSuccess = true
    }
    client.plugin(HttpSend).intercept { sender->
        execute(sender).apply {
            Log.e("intercept", request.url.toString()+ " => " + response.status.toString())
        }
    }
    return client
}
private fun authorizedClient(serverUrl: String, appRepos: AppRepos) : HttpClient {
    val client = HttpClient(Android){
        expectSuccess = true
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
        defaultRequest {
            url(serverUrl)
        }
    }
    client.plugin(HttpSend).intercept { sender->
        sender.header(HttpHeaders.Authorization, appRepos.getAccessToken())
        execute(sender).apply {
            Log.e("intercept", request.url.toString()+ " => " + response.status.toString())
        }
    }
    return client
}
