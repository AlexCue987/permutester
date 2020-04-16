package org.kollektions.examples.tdd

import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertTrue

data class RobotChangedBySveta(val weight: BigDecimal, val energyPerJump: BigDecimal){
    fun walk(){}
    fun maxJumpSpeed() = sqrt(energyPerJump.divide(weight).toDouble())
}

class RobotChangedBySvetaTest {
    @Test
    fun `can walk`() {
        val sut = RobotV0(weight = BigDecimal.ONE)
        sut.walk()
    }

    @Test
    fun `computes maxJumpSpeed`() {
        val sut = RobotChangedBySveta(
            weight = BigDecimal.ONE,
            energyPerJump = BigDecimal("16")
        )
        assertTrue(abs(sut.maxJumpSpeed() - 4.0) < 0.000001)
    }
}

