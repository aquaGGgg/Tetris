package com.example.tetris

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var tetrisView: TetrisView
    private lateinit var scoreTextView: TextView
    private val game = TetrisGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tetrisView = findViewById(R.id.tetrisView)
        scoreTextView = findViewById(R.id.scoreTextView)

        game.setOnScoreChangeListener { score ->
            runOnUiThread {
                scoreTextView.text = "Score: $score"
            }
        }

        game.onGameOver = {
            val currentScore = game.getScore()
            if (currentScore > SessionManager.maxScore) {
                SessionManager.maxScore = currentScore
            }
            runOnUiThread {
                finish()
            }
        }

        tetrisView.setGame(game)
        game.start()

        // Кнопки управления
        findViewById<Button>(R.id.btnLeft).setOnClickListener {
            game.moveLeft()
            tetrisView.invalidate()
        }
        findViewById<Button>(R.id.btnRight).setOnClickListener {
            game.moveRight()
            tetrisView.invalidate()
        }
        findViewById<Button>(R.id.btnRotate).setOnClickListener {
            game.rotateCurrentTetromino()
            tetrisView.invalidate()
        }
        findViewById<Button>(R.id.btnDown).setOnClickListener {
            game.moveDown()
            tetrisView.invalidate()
        }
    }
}