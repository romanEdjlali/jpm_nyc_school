package com.roman.jpmnycsch.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/*
* Class: SATScoreDao
* Owner: Roman Edjlali
* Date Created: 08/31/2023 18:43
*/
@Dao
interface SATScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSATScore(vararg satScore: SATScore): List<Long> //Return a list of primary key of SATScore insert

    /*@Insert
    suspend fun insertAllSATScores(vararg satScore: List<SATScore>)*/

    @Query("SELECT * FROM sat_scores")
    suspend fun getAllSATScores(): List<SATScore>

    @Query("SELECT * FROM sat_scores WHERE uuid = :ssId")
    suspend fun getSatScore(ssId: Int): SATScore

    @Query("SELECT * FROM sat_scores WHERE dbn = :dbnIndex")
    suspend fun getSatScoreByDbn(dbnIndex: String): SATScore

    @Query("DELETE FROM sat_scores where dbn = :dbnIndex")
    suspend fun deleteSAtScore(dbnIndex: String)

    @Query("DELETE FROM sat_scores")
    suspend fun deleteAllSAtScore()

}
