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

            // Вычисляем, сколько строк помещается в видимой области
            val visibleRows = height / cellSize

            // Если игровая сетка больше видимой, сдвигаем её вверх,
            // чтобы нижняя часть грида совпадала с TetrisView
            val yOffset = if (g.rows > visibleRows) (g.rows - visibleRows) * cellSize else 0f

            // Заливаем фон
            canvas.drawColor(Color.BLACK)

            // Отрисовка зафиксированных блоков
            for (y in 0 until g.rows) {
                for (x in 0 until g.cols) {
                    val cell = g.grid[y][x]
                    if (cell != 0) {
                        paint.color = cell
                        val top = y * cellSize - yOffset
                        val bottom = (y + 1) * cellSize - yOffset
                        // Рисуем только если блок находится в видимой области
                        if (bottom > 0 && top < height) {
                            canvas.drawRect(
                                x * cellSize,
                                top,
                                (x + 1) * cellSize,
                                bottom,
                                paint
                            )
                        }
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
                    val top = y * cellSize - yOffset
                    val bottom = (y + 1) * cellSize - yOffset
                    if (bottom > 0 && top < height) {
                        canvas.drawRect(
                            x * cellSize,
                            top,
                            (x + 1) * cellSize,
                            bottom,
                            paint
                        )
                    }
                }
            }
        }
    }
}
