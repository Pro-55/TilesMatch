package com.example.tilesmatch.utils

import android.view.MotionEvent
import com.example.tilesmatch.enums.MoveDirection
import kotlin.math.abs

object TouchHelperUtils {

    fun getMoveDirection(
        mE: MotionEvent,
        x: Float,
        y: Float
    ): MoveDirection {
        val dx = mE.rawX - x
        val dy = mE.rawY - y
        val isXNegative = dx < 0
        val isYNegative = dy < 0
        val isHorizontal = abs(dx) > abs(dy)
        return if (isHorizontal) {
            if (isXNegative) MoveDirection.LEFT else MoveDirection.RIGHT
        } else {
            if (isYNegative) MoveDirection.UP else MoveDirection.DOWN
        }
    }
}