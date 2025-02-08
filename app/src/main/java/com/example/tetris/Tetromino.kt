package com.example.tetris

import android.graphics.Color
import android.graphics.Point
import kotlin.random.Random

class Tetromino(val rotations: Array<Array<Point>>, val color: Int) {
    val rotationCount: Int get() = rotations.size

    fun getCells(rotation: Int): Array<Point> {
        return rotations[rotation % rotationCount]
    }

    companion object {
        private val tetrominoes = listOf(
            // I-подобная фигура
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(-1, 0), Point(0, 0), Point(1, 0), Point(2, 0)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(0, 1), Point(0, 2))
                ),
                color = Color.CYAN
            ),
            // O-подобная фигура
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(0, 0), Point(1, 0), Point(0, 1), Point(1, 1))
                ),
                color = Color.YELLOW
            ),
            // T-подобная фигура
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(-1, 0), Point(0, 0), Point(1, 0), Point(0, 1)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(0, 1), Point(1, 0)),
                    arrayOf(Point(-1, 0), Point(0, 0), Point(1, 0), Point(0, -1)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(0, 1), Point(-1, 0))
                ),
                color = Color.MAGENTA
            ),
            // S-подобная фигура
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(0, 0), Point(1, 0), Point(-1, 1), Point(0, 1)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(1, 0), Point(1, 1))
                ),
                color = Color.GREEN
            ),
            // Z-подобная фигура
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(-1, 0), Point(0, 0), Point(0, 1), Point(1, 1)),
                    arrayOf(Point(1, -1), Point(0, 0), Point(1, 0), Point(0, 1))
                ),
                color = Color.RED
            ),
            // J-подобная фигура
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(-1, -1), Point(-1, 0), Point(0, 0), Point(1, 0)),
                    arrayOf(Point(0, -1), Point(1, -1), Point(0, 0), Point(0, 1)),
                    arrayOf(Point(-1, 0), Point(0, 0), Point(1, 0), Point(1, 1)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(-1, 1), Point(0, 1))
                ),
                color = Color.BLUE
            ),
            // L-подобная фигура
            Tetromino(
                rotations = arrayOf(
                    arrayOf(Point(1, -1), Point(-1, 0), Point(0, 0), Point(1, 0)),
                    arrayOf(Point(0, -1), Point(0, 0), Point(0, 1), Point(1, 1)),
                    arrayOf(Point(-1, 0), Point(0, 0), Point(1, 0), Point(-1, 1)),
                    arrayOf(Point(-1, -1), Point(0, -1), Point(0, 0), Point(0, 1))
                ),
                color = Color.rgb(255, 165, 0) // оранжевый
            )
        )

        fun random(): Tetromino {
            return tetrominoes.random(Random)
        }
    }
}
