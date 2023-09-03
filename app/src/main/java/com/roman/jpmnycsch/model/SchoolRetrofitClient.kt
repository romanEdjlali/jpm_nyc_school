package com.roman.jpmnycsch.model

import android.annotation.SuppressLint
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* Class: SchoolRetrofitClient
* Owner: Roman Edjlali
* Date Created: 08/23/2023 19:27
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
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build().create(SchoolApiService::class.java)

    }

   fun getSatScore(): Single<List<SATScore>> { //Just using RXJava here Single
        return getInstance().getSatScores()
    }
}