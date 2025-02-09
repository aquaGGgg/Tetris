package com.example.tetris

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    private lateinit var maxScoreTextView: TextView
    private lateinit var newGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        maxScoreTextView = findViewById(R.id.maxScoreTextView)
        newGameButton = findViewById(R.id.newGameButton)

        updateMaxScoreText()

        newGameButton.setOnClickListener {
            // Запускаем GameActivity
            startActivity(Intent(this, GameActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        updateMaxScoreText()
    }

    private fun updateMaxScoreText() {
        maxScoreTextView.text = "Max Score: ${SessionManager.maxScore}"
    }
}
