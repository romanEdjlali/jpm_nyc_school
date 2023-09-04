package com.roman.jpmnycsch.model

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/*
* Class: SchoolDao
* Owner: Roman Edjlali
* Date Created: 09/03/2023 15:14
*/

interface SchoolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSchools(vararg school: School)

    @Query("SELECT * FROM schools")
    suspend fun getAllSchools(): List<School>

    @Query("SELECT * FROM schools WHERE dbn = :uuId")
    suspend fun getSchool(uuId: Int): School

    @Query("DELETE FROM schools")
    suspend fun deleteAllSchools()
}