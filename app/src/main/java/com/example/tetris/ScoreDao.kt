package com.example.tetris

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: Score): Long

    @Query("SELECT * FROM scores ORDER BY score DESC LIMIT 5")
    suspend fun getTopScores(): List<Score>
}
