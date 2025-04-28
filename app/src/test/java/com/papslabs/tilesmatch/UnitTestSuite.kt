package com.papslabs.tilesmatch

import com.papslabs.tilesmatch.utils.MoveHelperTestSuite
import com.papslabs.tilesmatch.utils.TouchHelperTestSuite
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MoveHelperTestSuite::class,
    TouchHelperTestSuite::class
)
class UnitTestSuite