package com.roman.jpmnycsch.view

import com.roman.jpmnycsch.model.School
import retrofit2.Response
import retrofit2.http.GET

/*
* Interface: SchoolApiService
* Owner: Roman Edjlali
* Date Created: 08/31/2023 19:16 PM
*/

interface SchoolApiService {
    //https://data.cityofnewyork.us/resource/s3k6-pzi2.json
    @GET("resource/s3k6-pzi2.json")
    suspend fun getAllSchools(): Response<List<School>>

    // Similar interface for fetching SAT scores
}

fun getSchools(){

}