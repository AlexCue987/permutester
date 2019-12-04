package org.kollektions.examples.robot

import org.kollektions.permutester.Permutations
import org.kollektions.permutester.getMembersAnnotationValues
import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertTrue

data class RobotWithFixedBug(val weight: BigDecimal, val energyPerJump: BigDecimal){
    val itemsToCarry = mutableListOf<ItemToCarry>()

    fun walk(){}

    fun carry(itemToCarry: ItemToCarry) = itemsToCarry.add(itemToCarry)

    fun dropItems() = itemsToCarry.clear()

    fun totalWeight():BigDecimal {
        var ret = weight
        itemsToCarry.forEach { ret = ret.add(it.weight) }
        return ret
    }

    fun maxJumpSpeed() = sqrt(energyPerJump.divide(totalWeight()).toDouble())
}

class RobotWithFixedBugTest {
    val sut =
        RobotWithFixedBug(weight = BigDecimal.ONE, energyPerJump = BigDecimal("16"))

    @RobotTestCase(motionType = MotionType.Walk, loadType = LoadType.None)
    @Test
    fun `can walk`() {
        sut.walk()
    }

    @RobotTestCase(motionType = MotionType.Walk, loadType = LoadType.OneItem)
    @Test
    fun `can carry item`() {
        sut.carry(ItemToCarry(BigDecimal.ONE))
    }

    @Test
    fun `can drop item`() {
        sut.carry(ItemToCarry(BigDecimal.ONE))
        sut.dropItems()
    }

    @RobotTestCase(motionType = MotionType.Jump, loadType = LoadType.None)
    @Test
    fun `computes maxJumpSpeed without load`() {
        assertTrue(abs(sut.maxJumpSpeed() - 4.0) < 0.000001)
    }

    @RobotTestCase(motionType = MotionType.Jump, loadType = LoadType.OneItem)
    @Test
    fun `computes maxJumpSpeed when carrying an item`() {
        sut.carry(ItemToCarry(BigDecimal("3")))
        assertTrue(abs(sut.maxJumpSpeed() - 2.0) < 0.000001)
    }

    enum class MotionType {Walk, Jump}

    enum class LoadType {None, OneItem}

    val expectedPermutations = Permutations(MotionType.values().toList(), LoadType.values().toList())

    private annotation class RobotTestCase(val motionType: MotionType, val loadType: LoadType)

    @Test
    fun `ensures all permutations tested`() {
        val actualPermutations = getMembersAnnotationValues<RobotTestCase>(this) { listOf(it.motionType, it.loadType) }
        expectedPermutations.match(actualPermutations)
    }
}

