package org.kollektions.examples.tdd

import java.math.BigDecimal
import kotlin.test.Test

data class RobotV0(val weight: BigDecimal){
    fun walk(){}
}

class RobotV0Test {
    @Test
    fun `can walk`() {
        val sut = RobotV0(weight = BigDecimal.ONE)
        sut.walk()
    }
}

