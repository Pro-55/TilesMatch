package com.papslabs.tilesmatch.utils.helpers

import com.papslabs.tilesmatch.enums.MoveDirection

object MoveHelperUtils {

    /**
     * calculates column and row for given position
     * and returns target position for the move
     * if the move is not valid for given position then returns -1
     *
     * @param position current position
     * @param direction direction of the move
     * @return target position
     */
    fun getValidTargetPosition(
        position: Int,
        direction: MoveDirection
    ): Int {
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