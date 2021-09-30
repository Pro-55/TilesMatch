package com.example.tilesmatch.utils

import com.example.tilesmatch.enums.MoveDirection
import org.junit.Test
import org.junit.runner.RunWith

class MoveHelperTestSuite {

    @Test
    fun test_0_canMoveLeft() {
        assert(MoveHelperUtils.getValidTargetPosition(0, MoveDirection.LEFT) == -1)
    }

    @Test
    fun test_1_canMoveLeft() {
        assert(MoveHelperUtils.getValidTargetPosition(3, MoveDirection.LEFT) == 2)
    }

    @Test
    fun test_2_canMoveUp() {
        assert(MoveHelperUtils.getValidTargetPosition(0, MoveDirection.UP) == -1)
    }

    @Test
    fun test_3_canMoveUp() {
        assert(MoveHelperUtils.getValidTargetPosition(15, MoveDirection.UP) == 11)
    }

    @Test
    fun test_4_canMoveRight() {
        assert(MoveHelperUtils.getValidTargetPosition(3, MoveDirection.RIGHT) == -1)
    }

    @Test
    fun test_5_canMoveRight() {
        assert(MoveHelperUtils.getValidTargetPosition(0, MoveDirection.RIGHT) == 1)
    }

    @Test
    fun test_6_canMoveDown() {
        assert(MoveHelperUtils.getValidTargetPosition(15, MoveDirection.DOWN) == -1)
    }

    @Test
    fun test_7_canMoveDown() {
        assert(MoveHelperUtils.getValidTargetPosition(0, MoveDirection.DOWN) == 4)
    }
}