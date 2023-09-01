package com.roman.jpmnycsch.view

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* Class: SchoolRetrofitClient
* Owner: Roman Edjlali
* Date Created: 08/23/2023 19:27 PM
*/

object SchoolRetrofitClient {
    private const val BASE_URL = "https://data.cityofnewyork.us/"

    @SuppressLint("SuspiciousIndentation")
    fun getInstance(): SchoolApiService {
        var mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        var mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build().create(SchoolApiService::class.java)

    }
}