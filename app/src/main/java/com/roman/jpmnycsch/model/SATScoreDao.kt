package com.roman.jpmnycsch.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SATScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSATScores(vararg satScore: SATScore)//: List<SATScore>

    @Query("SELECT * FROM sat_scores")
    suspend fun getAllSATScores(): List<SATScore>

    @Query("SELECT * FROM sat_scores WHERE uuid = :ssId")
    suspend fun getSatScore(ssId: Int): SATScore

    @Query("DELETE FROM sat_scores")
    suspend fun deleteAllSAtScore()

}
