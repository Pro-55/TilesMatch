package com.example.tilesmatch

import com.example.tilesmatch.utils.MoveHelperTestSuite
import com.example.tilesmatch.utils.TouchHelperTestSuite
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MoveHelperTestSuite::class,
    TouchHelperTestSuite::class
)
class UnitTestSuite