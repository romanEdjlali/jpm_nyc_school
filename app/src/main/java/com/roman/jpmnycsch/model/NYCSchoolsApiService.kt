package com.roman.jpmnycsch.model

import android.annotation.SuppressLint
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/*
* Class: NYCSchoolsApiService
* Owner: Roman Edjlali
* Date Created: 08/23/2023 19:27
*/

object NYCSchoolsApiService {
    private const val BASE_URL = "https://data.cityofnewyork.us/"

    @SuppressLint("SuspiciousIndentation")
    fun getInstance(): NYCSchoolsApi {
        var mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        var mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build().create(NYCSchoolsApi::class.java)

    }

   fun getSatScore(): Single<List<SATScore>> { //Just using RXJava here Single
        return getInstance().getSatScores()
    }
}