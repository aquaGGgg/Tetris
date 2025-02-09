package com.example.tetris

import android.os.Handler
import android.os.Looper

class TetrisGame {
    val rows = 20
    val cols = 10
    val grid: Array<IntArray> = Array(rows) { IntArray(cols) }

    private var currentTetromino: Tetromino? = null
    private var currentX = cols / 2
    private var currentY = 0
    private var currentRotation = 0

    // dropProgress накапливает дробное смещение между целочисленными перемещениями вниз
    private var dropProgress = 0f
    private val FALL_DURATION = 500L

    private val scoreManager = ScoreManager()
    var onGameOver: (() -> Unit)? = null
    private var scoreChangeListener: ((Int) -> Unit)? = null

    fun setOnScoreChangeListener(listener: (Int) -> Unit) {
        scoreChangeListener = listener
    }

    private val frameHandler = Handler(Looper.getMainLooper())
    private var lastUpdateTime = System.currentTimeMillis()
    private val frameRunnable = object : Runnable {
        override fun run() {
            val now = System.currentTimeMillis()
            val dt = now - lastUpdateTime
            lastUpdateTime = now
            update(dt)
            frameHandler.postDelayed(this, 16)
        }
    }

    fun start() {
        resetGame()
        frameHandler.post(frameRunnable)
    }

    private fun update(dt: Long) {
        dropProgress += dt.toFloat() / FALL_DURATION

        currentTetromino?.let { tetro ->
            val maxOffset = tetro.getCells(currentRotation).maxOf { it.y }
            val allowedProgress = (rows - 1 - currentY - maxOffset).toFloat()

            if (allowedProgress <= 0f) {
                dropProgress = 0f
                fixCurrentTetromino()
                clearLines()
                spawnTetromino()
                return
            }
            dropProgress = dropProgress.coerceAtMost(allowedProgress)
        }

        while (dropProgress >= 1f) {
            dropProgress -= 1f
            if (!moveCurrentTetromino(0, 1)) {
                dropProgress = 0f
                fixCurrentTetromino()
                clearLines()
                spawnTetromino()
            }
        }
    }

    fun getDropProgress() = dropProgress

    fun moveCurrentTetromino(dx: Int, dy: Int): Boolean {
        val newX = currentX + dx
        val newY = currentY + dy
        return if (isValidPosition(newX, newY, currentRotation)) {
            currentX = newX
            currentY = newY
            true
        } else {
            false
        }
    }

    private fun isValidPosition(x: Int, y: Int, rotation: Int): Boolean {
        currentTetromino?.let { tetro ->
            for (point in tetro.getCells(rotation)) {
                val newX = x + point.x
                val newY = y + point.y

                if (newX < 0 || newX >= cols) return false
                if (newY >= rows) return false
                if (newY >= 0 && grid[newY][newX] != 0) return false
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
        for (y in rows - 1 downTo 0) {
            if (grid[y].all { it != 0 }) {
                System.arraycopy(grid, 0, grid, 1, y)
                grid[0] = IntArray(cols)
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
        currentX = cols / 2 - 1  // Центрирование фигуры (особенно для I-формы)
        currentY = 0
        currentRotation = 0

        if (!isValidPosition(currentX, currentY, currentRotation)) {
            onGameOver?.invoke()
        }
    }

    fun rotateCurrentTetromino() {
        currentTetromino?.let { tetro ->
            val newRotation = (currentRotation + 1) % tetro.rotationCount
            val offsets = listOf(0, 1, -1, 2, -2)  // Приоритет смещений при повороте

            for (dx in offsets) {
                if (isValidPosition(currentX + dx, currentY, newRotation)) {
                    currentX += dx
                    currentRotation = newRotation
                    return
                }
            }
        }
    }

    fun moveLeft() = moveCurrentTetromino(-1, 0)
    fun moveRight() = moveCurrentTetromino(1, 0)

    fun moveDown() {
        if (!moveCurrentTetromino(0, 1)) {
            dropProgress = 0f
            fixCurrentTetromino()
            clearLines()
            spawnTetromino()
        }
    }

    fun resetGame() {
        grid.forEach { it.fill(0) }
        scoreManager.reset()
        scoreChangeListener?.invoke(0)
        spawnTetromino()
        dropProgress = 0f
    }

    fun getScore(): Int = scoreManager.getScore()
    fun getCurrentTetromino(): Tetromino? = currentTetromino
    fun getCurrentX(): Int = currentX
    fun getCurrentY(): Int = currentY
    fun getCurrentRotation(): Int = currentRotation

    // Новый метод для проверки, можно ли сдвинуть фигуру вниз на одну клетку
    fun canMoveDown(): Boolean {
        return isValidPosition(currentX, currentY + 1, currentRotation)
    }
}
