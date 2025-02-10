package com.example.tetris

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScoreManager(context: Context) {
    private var score: Int = 0
    private val db = ScoreDatabase.getDatabase(context)
    private val scoreDao = db.scoreDao()

    fun addScore(points: Int) {
        score += points
    }

    fun getScore(): Int = score

    fun reset() {
        score = 0
    }

    fun saveScore(playerName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            scoreDao.insertScore(Score(playerName = playerName, score = score))
        }
    }

    fun getTopScores(callback: (List<Score>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val scores = scoreDao.getTopScores()
            withContext(Dispatchers.Main) {
                callback(scores)
            }
        }
    }
}
