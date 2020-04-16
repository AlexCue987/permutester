package org.kollektions.examples.tdd

import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertTrue

data class RobotWithMergedChangesAndBug(val weight: BigDecimal, val energyPerJump: BigDecimal){
    val itemsToCarry = mutableListOf<ItemToCarry>()

    fun walk(){}

    fun carry(itemToCarry: ItemToCarry) = itemsToCarry.add(itemToCarry)

    fun dropItems() = itemsToCarry.clear()

    fun maxJumpSpeed() = sqrt(energyPerJump.divide(weight).toDouble())
}

class RobotWithMergedChangesAndBugTest {
    val sut = RobotWithMergedChangesAndBug(
        weight = BigDecimal.ONE,
        energyPerJump = BigDecimal("16")
    )

    @Test
    fun `can walk`() {
        sut.walk()
    }

    @Test
    fun `can carry item and walk`() {
        sut.carry(ItemToCarry(BigDecimal.ONE))
        sut.walk()
    }

    @Test
    fun `can drop item and walk`() {
        sut.carry(ItemToCarry(BigDecimal.ONE))
        sut.dropItems()
        sut.walk()
    }

    @Test
    fun `computes maxJumpSpeed`() {
        assertTrue(abs(sut.maxJumpSpeed() - 4.0) < 0.000001)
    }
}

