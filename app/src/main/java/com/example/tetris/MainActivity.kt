package com.example.tetris

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tetrisView: TetrisView
    private lateinit var scoreTextView: TextView
    private val game = TetrisGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tetrisView = findViewById(R.id.tetrisView)
        scoreTextView = findViewById(R.id.scoreTextView)

        // Обновление счета
        game.setOnScoreChangeListener { score ->
            runOnUiThread {
                scoreTextView.text = "Score: $score"
            }
        }

        // Автоматическая перерисовка
        game.setOnGameUpdateListener {
            runOnUiThread {
                tetrisView.invalidate()
            }
        }

        tetrisView.setGame(game)
        game.start()

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
