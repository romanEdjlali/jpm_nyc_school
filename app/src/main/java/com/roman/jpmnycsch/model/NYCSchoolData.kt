package com.roman.jpmnycsch.model

import com.google.gson.annotations.SerializedName

/*
* Class: NYCSchoolData
* Owner: Roman Edjlali
* Date Created: 08/31/2023 19:08 PM
*/

data class School(
    //@SerializedName("dbn")
    val dbn: String,
    //@SerializedName("school_name")
    val school_name: String,
    // Other relevant fields
)

data class SatScore(
    val dbn: String,
    val num_of_sat_test_takers: Int,
    val sat_critical_reading_avg_score: Int,
    val sat_math_avg_score: Int,
    val sat_writing_avg_score: Int,
    // Other relevant fields
)
