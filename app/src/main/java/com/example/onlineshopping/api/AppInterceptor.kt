package com.example.onlineshopping.api

import com.example.onlineshopping.utils.PrefUtils
import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        if (!PrefUtils.getToken().isNullOrEmpty()){
            original.newBuilder().addHeader("token", PrefUtils.getToken())
        }
        return chain.proceed(original)
    }

}