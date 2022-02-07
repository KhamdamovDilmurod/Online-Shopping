package com.example.onlineshopping.api

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.onlineshopping.screen.profil.MyApp
import com.example.onlineshopping.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    var retrofit: Retrofit? = null
    fun apiClient(): Api{
        if(retrofit==null){
            val  client = OkHttpClient().newBuilder()
                .addInterceptor(
                    ChuckerInterceptor.Builder(MyApp.app)
                        .collector(ChuckerCollector(MyApp.app))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                )
                .addInterceptor {
                    val request = it.request().newBuilder().build()
                    return@addInterceptor it.proceed(request)
                }
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }
        return retrofit!!.create(Api::class.java)
    }
}