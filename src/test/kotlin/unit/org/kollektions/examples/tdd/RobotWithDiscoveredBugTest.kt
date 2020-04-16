package org.kollektions.examples.tdd

import org.kollektions.permutester.Permutations
import org.kollektions.permutester.getMembersAnnotationValues
import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

data class RobotWithDiscoveredBug(val weight: BigDecimal, val energyPerJump: BigDecimal){
    val itemsToCarry = mutableListOf<ItemToCarry>()

    fun walk(){}

    fun carry(itemToCarry: ItemToCarry) = itemsToCarry.add(itemToCarry)

    fun dropItems() = itemsToCarry.clear()

    fun maxJumpSpeed() = sqrt(energyPerJump.divide(weight).toDouble())
}

class RobotWithDiscoveredBugTest {
    val sut =
        RobotWithDiscoveredBug(weight = BigDecimal.ONE, energyPerJump = BigDecimal("16"))

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
    fun `computes maxJumpSpeed`() {
        assertTrue(abs(sut.maxJumpSpeed() - 4.0) < 0.000001)
    }

    enum class MotionType {Walk, Jump}

    enum class LoadType {None, OneItem}

    val expectedPermutations = Permutations(MotionType.values().toList(), LoadType.values().toList())

    private annotation class RobotTestCase(val motionType: MotionType, val loadType: LoadType)

    @Test
    fun `finds missing test`() {
        val actualPermutations = getMembersAnnotationValues<RobotTestCase>(this) { listOf(it.motionType, it.loadType) }
        val actual = assertFailsWith<AssertionError> { expectedPermutations.match(actualPermutations) }
        assertEquals("Missing:\n[Jump, OneItem]", actual.message)
    }
}

