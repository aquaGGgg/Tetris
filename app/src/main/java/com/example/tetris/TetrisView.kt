package com.example.tetris

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class TetrisView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var game: TetrisGame? = null
    private val paint = Paint()

    fun setGame(game: TetrisGame) {
        this.game = game
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        game?.let { g ->
            val cellSize = width / g.cols.toFloat()
            // Фон
            canvas.drawColor(Color.BLACK)

            // Отрисовка зафиксированных блоков
            for (y in 0 until g.rows) {
                for (x in 0 until g.cols) {
                    val cell = g.grid[y][x]
                    if (cell != 0) {
                        paint.color = cell
                        canvas.drawRect(
                            x * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            paint
                        )
                    }
                }
            }

            // Отрисовка текущей фигуры
            val tetro = g.getCurrentTetromino()
            if (tetro != null) {
                paint.color = tetro.color
                val currentX = g.getCurrentX()
                val currentY = g.getCurrentY()
                val rotation = g.getCurrentRotation()
                for (point in tetro.getCells(rotation)) {
                    val x = currentX + point.x
                    val y = currentY + point.y
                    if (y >= 0) {
                        canvas.drawRect(
                            x * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            paint
                        )
                    }
                }
            }
        }
    }
}
