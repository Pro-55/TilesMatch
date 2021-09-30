package com.example.tilesmatch.utils

import com.example.tilesmatch.enums.MoveDirection
import org.junit.Test

class TouchHelperTestSuite {

    @Test
    fun test_0_shouldMoveLeft() {
        assert(TouchHelperUtils.getMoveDirection(-1F, 0F) == MoveDirection.LEFT)
    }

    @Test
    fun test_1_shouldMoveUp() {
        assert(TouchHelperUtils.getMoveDirection(0F, -1F) == MoveDirection.UP)
    }

    @Test
    fun test_2_shouldMoveRight() {
        assert(TouchHelperUtils.getMoveDirection(1F, 0F) == MoveDirection.RIGHT)
    }

    @Test
    fun test_3_shouldMoveDown() {
        assert(TouchHelperUtils.getMoveDirection(0F, 1F) == MoveDirection.DOWN)
    }

}