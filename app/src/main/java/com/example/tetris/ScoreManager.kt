package com.example.tetris

class ScoreManager {
    private var score: Int = 0

    fun addScore(points: Int) {
        score += points
    }

    fun getScore(): Int = score

    fun reset() {
        score = 0
    }
}
