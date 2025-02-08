package com.example.tetris

import android.os.Handler
import android.os.Looper

class TetrisGame {
    val rows = 20
    val cols = 10
    // Игровое поле: 0 – пустая клетка, ненулевое значение – цвет блока
    val grid: Array<IntArray> = Array(rows) { IntArray(cols) }

    private var currentTetromino: Tetromino? = null
    private var currentX = cols / 2
    private var currentY = 0
    private var currentRotation = 0

    private val scoreManager = ScoreManager()
    private var scoreChangeListener: ((Int) -> Unit)? = null

    // Новый слушатель для обновления экрана после каждого тика
    private var gameUpdateListener: (() -> Unit)? = null

    private val handler = Handler(Looper.getMainLooper())

    fun setOnScoreChangeListener(listener: (Int) -> Unit) {
        scoreChangeListener = listener
    }

    fun setOnGameUpdateListener(listener: () -> Unit) {
        gameUpdateListener = listener
    }

    fun start() {
        spawnTetromino()
        handler.postDelayed(tickRunnable, 500)
    }

    private val tickRunnable = object : Runnable {
        override fun run() {
            tick()
            // Вызываем обновление экрана после каждого тика
            gameUpdateListener?.invoke()
            handler.postDelayed(this, 500)
        }
    }

    private fun tick() {
        if (!moveCurrentTetromino(0, 1)) {
            fixCurrentTetromino()
            clearLines()
            spawnTetromino()
        }
    }

    fun moveCurrentTetromino(dx: Int, dy: Int): Boolean {
        val newX = currentX + dx
        val newY = currentY + dy
        if (isValidPosition(newX, newY, currentRotation)) {
            currentX = newX
            currentY = newY
            return true
        }
        return false
    }

    private fun isValidPosition(x: Int, y: Int, rotation: Int): Boolean {
        currentTetromino?.let { tetro ->
            for (point in tetro.getCells(rotation)) {
                val newX = x + point.x
                val newY = y + point.y
                // Если выходит за боковые или нижнюю границу – позиция недопустима
                if (newX < 0 || newX >= cols || newY >= rows) {
                    return false
                }
                // Если внутри поля уже есть блок – недопустимо
                if (newY >= 0 && grid[newY][newX] != 0) {
                    return false
                }
            }
        }
        return true
    }

    private fun fixCurrentTetromino() {
        currentTetromino?.let { tetro ->
            for (point in tetro.getCells(currentRotation)) {
                val newX = currentX + point.x
                val newY = currentY + point.y
                if (newY in 0 until rows && newX in 0 until cols) {
                    grid[newY][newX] = tetro.color
                }
            }
        }
    }

    private fun clearLines() {
        var linesCleared = 0
        for (y in 0 until rows) {
            if (grid[y].all { it != 0 }) {
                // Сдвигаем все строки выше вниз
                for (i in y downTo 1) {
                    grid[i] = grid[i - 1].copyOf()
                }
                grid[0] = IntArray(cols) { 0 }
                linesCleared++
            }
        }
        if (linesCleared > 0) {
            scoreManager.addScore(linesCleared * 100)
            scoreChangeListener?.invoke(scoreManager.getScore())
        }
    }

    private fun spawnTetromino() {
        currentTetromino = Tetromino.random()
        currentX = cols / 2
        currentY = 0
        currentRotation = 0
        // Если новая фигура не помещается – сбрасываем игру
        if (!isValidPosition(currentX, currentY, currentRotation)) {
            resetGame()
        }
    }

    fun rotateCurrentTetromino() {
        val newRotation = (currentRotation + 1) % (currentTetromino?.rotationCount ?: 1)
        if (isValidPosition(currentX, currentY, newRotation)) {
            currentRotation = newRotation
        }
    }

    fun moveLeft() {
        moveCurrentTetromino(-1, 0)
    }

    fun moveRight() {
        moveCurrentTetromino(1, 0)
    }

    fun moveDown() {
        if (!moveCurrentTetromino(0, 1)) {
            fixCurrentTetromino()
            clearLines()
            spawnTetromino()
        }
    }

    fun resetGame() {
        for (y in 0 until rows) {
            for (x in 0 until cols) {
                grid[y][x] = 0
            }
        }
        scoreManager.reset()
        scoreChangeListener?.invoke(scoreManager.getScore())
        spawnTetromino()
    }

    // Геттеры для отображения текущей фигуры
    fun getCurrentTetromino(): Tetromino? = currentTetromino
    fun getCurrentX(): Int = currentX
    fun getCurrentY(): Int = currentY
    fun getCurrentRotation(): Int = currentRotation
}
