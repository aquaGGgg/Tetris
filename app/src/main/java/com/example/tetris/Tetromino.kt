package com.example.tetris

import android.graphics.Color
import android.graphics.Point

class Tetromino(val rotations: Array<Array<Point>>, val color: Int) {
    val rotationCount: Int get() = rotations.size

    fun getCells(rotation: Int): Array<Point> {
        return rotations[rotation % rotationCount]
    }

    companion object {
        private val tetrominoes = listOf(
            // I-форма
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(-1, 0), Point(0, 0), Point(1, 0), Point(2, 0)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(0, 1), Point(0, 2))
                ),
                color = Color.CYAN
            ),
            // O-форма
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(0, 0), Point(1, 0), Point(0, 1), Point(1, 1))
                ),
                color = Color.YELLOW
            ),
            // T-форма
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(-1, 0), Point(0, 0), Point(1, 0), Point(0, 1)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(0, 1), Point(1, 0)),
                    arrayOf(Point(-1, 0), Point(0, 0), Point(1, 0), Point(0, -1)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(0, 1), Point(-1, 0))
                ),
                color = Color.MAGENTA
            ),
            // S-форма
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(0, 0), Point(1, 0), Point(-1, 1), Point(0, 1)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(1, 0), Point(1, 1))
                ),
                color = Color.GREEN
            ),
            // Z-форма
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(-1, 0), Point(0, 0), Point(0, 1), Point(1, 1)),
                    arrayOf(Point(1, -1), Point(0, 0), Point(1, 0), Point(0, 1))
                ),
                color = Color.RED
            ),
            // J-форма
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(-1, -1), Point(-1, 0), Point(0, 0), Point(1, 0)),
                    arrayOf(Point(0, -1), Point(1, -1), Point(0, 0), Point(0, 1)),
                    arrayOf(Point(-1, 0), Point(0, 0), Point(1, 0), Point(1, 1)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(-1, 1), Point(0, 1))
                ),
                color = Color.BLUE
            ),
            // L-форма
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(1, -1), Point(-1, 0), Point(0, 0), Point(1, 0)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(0, 1), Point(1, 1)),
                    arrayOf(Point(-1, 0), Point(0, 0), Point(1, 0), Point(-1, 1)),
                    arrayOf(Point(-1, -1), Point(0, -1), Point(0, 0), Point(0, 1))
                ),
                color = Color.rgb(255, 165, 0)
            )
        )

        fun random(): Tetromino {
            return tetrominoes.random()
        }
    }
}
