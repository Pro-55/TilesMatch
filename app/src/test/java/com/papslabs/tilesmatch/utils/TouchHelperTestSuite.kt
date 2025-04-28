package com.papslabs.tilesmatch.utils

import com.papslabs.tilesmatch.enums.MoveDirection
import com.papslabs.tilesmatch.utils.helpers.TouchHelperUtils
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