package com.example.tetris

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class TetrisView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var game: TetrisGame? = null
    private val paint = Paint().apply { style = Paint.Style.FILL }

    fun setGame(game: TetrisGame) {
        this.game = game
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        game?.let { g ->
            val cellSize = width.toFloat() / g.cols
            val totalHeight = cellSize * g.rows

            // Отрисовка фона игрового поля
            canvas.drawRect(0f, 0f, width.toFloat(), totalHeight, Paint().apply {
                color = Color.BLACK
            })

            // Отрисовка зафиксированных блоков (из grid)
            for (y in 0 until g.rows) {
                for (x in 0 until g.cols) {
                    if (g.grid[y][x] != 0) {
                        paint.color = g.grid[y][x]
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

            // Определяем effectiveY для отрисовки текущей фигуры.
            // Если движение вниз невозможно (фигура почти достигла дна или соприкасается с зафиксированными блоками),
            // используем целое положение currentY без дробного смещения.
            val effectiveY = if (!g.canMoveDown()) {
                g.getCurrentY().toFloat()
            } else {
                g.getCurrentY() + g.getDropProgress()
            }

            // Отрисовка текущей фигуры (даже если она частично находится выше поля)
            g.getCurrentTetromino()?.let { tetro ->
                paint.color = tetro.color
                val rotation = g.getCurrentRotation()
                tetro.getCells(rotation).forEach { point ->
                    val yPos = (effectiveY + point.y) * cellSize
                    if (yPos + cellSize >= 0) { // Рисуем только видимую часть
                        canvas.drawRect(
                            (g.getCurrentX() + point.x) * cellSize,
                            yPos,
                            (g.getCurrentX() + point.x + 1) * cellSize,
                            yPos + cellSize,
                            paint
                        )
                    }
                }
            }
            // Вызываем перерисовку. Если у вас игровой цикл сам обновляет экран,
            // можно убрать этот вызов.
            invalidate()
        }
    }
}
