package com.example.washingreminder.utils

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class HttpAccessor {
    // 中略
    suspend fun getJson(url: String): JSONObject {
        return withContext(Dispatchers.IO) {
            val (_, _, result) = url.httpGet().responseJson()

            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()

                    JSONObject(mapOf("message" to ex.toString()))
                }

                is Result.Success -> {
                    result.get().obj()
                }
            }
        }
    }
}