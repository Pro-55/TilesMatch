package com.example.tilesmatch.utils

import com.example.tilesmatch.enums.MoveDirection

object MoveHelperUtils {

    fun getValidTargetPosition(position: Int, direction: MoveDirection): Int {
        val row = position / 4
        val column = position - (row * 4)
        return when (direction) {
            MoveDirection.LEFT -> if (column > 0) position - 1 else -1
            MoveDirection.UP -> if (row > 0) position - 4 else -1
            MoveDirection.RIGHT -> if (column < 3) position + 1 else -1
            MoveDirection.DOWN -> if (row < 3) position + 4 else -1
        }
    }
}