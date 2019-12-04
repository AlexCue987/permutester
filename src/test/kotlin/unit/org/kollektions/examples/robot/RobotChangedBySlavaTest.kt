package org.kollektions.examples.robot

import java.math.BigDecimal
import kotlin.test.Test

data class RobotChangedBySlava(val weight: BigDecimal){
    val itemsToCarry = mutableListOf<ItemToCarry>()

    fun walk(){}

    fun carry(itemToCarry: ItemToCarry) = itemsToCarry.add(itemToCarry)

    fun dropItems() = itemsToCarry.clear()
}

data class ItemToCarry(val weight: BigDecimal)

class RobotChangedBySlavaTest {
    val sut = RobotChangedBySlava(weight = BigDecimal.ONE)

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
}

