package com.roman.jpmnycsch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Class: NYCSchoolData
* Owner: Roman Edjlali
* Date Created: 08/31/2023 19:08
*/

@Entity(tableName = "schools")
data class School(
    //@SerializedName("dbn")
    val dbn: String,
    //@SerializedName("school_name")
    val school_name: String,
    //val borough: String,
    //val overviewParagraph: String
    val total_students: String,
    val location: String,
    val phone_number: String
)

@Entity(tableName = "sat_scores")
data class SATScore(

    @ColumnInfo(name = "dbn")
    val dbn: String,

    @ColumnInfo(name = "school_name")
    val school_name: String,

    @ColumnInfo(name = "num_of_sat_test_takers")
    val num_of_sat_test_takers: String?,

    @ColumnInfo(name = "sat_critical_reading_avg_score")
    val sat_critical_reading_avg_score: String?,

    @ColumnInfo(name = "sat_math_avg_score")
    val sat_math_avg_score: String?,

    @ColumnInfo(name = "sat_writing_avg_score")
    val sat_writing_avg_score: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Long = 0
}
