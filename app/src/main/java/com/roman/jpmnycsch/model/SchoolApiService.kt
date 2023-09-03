package com.roman.jpmnycsch.model

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

/*
* Interface: SchoolApiService
* Owner: Roman Edjlali
* Date Created: 08/31/2023 19:16
*/

interface SchoolApiService {
    //https://data.cityofnewyork.us/resource/s3k6-pzi2.json
    @GET("resource/s3k6-pzi2.json")
    suspend fun getAllSchools(): Response<List<School>>

    // https://data.cityofnewyork.us/resource/f9bf-2cp4.json
    //@GET("resource/f9bf-2cp4.json")
    //suspend fun getSatScores(): Response<List<SATScore>>

    @GET("resource/f9bf-2cp4.json")
    fun getSatScores(): Single<List<SATScore>>
}
