package org.kollektions.permutester

import java.lang.IllegalArgumentException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class InterfaceMethodsReaderTest: MandatoryRoverTests {
    override fun canMove() {}

    override fun canStop() {}

    @Test
    fun `reads interface methods`() {
        val actual = getInterfaceMethodNames(this, "org.kollektions.permutester.MandatoryRoverTests")
        assertEquals(setOf("canMove", "canStop"), actual.toSet())
    }

    @Test
    fun `handles wrong interface name`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            getInterfaceMethodNames(this, "No.Such.Name")
        }
         assertEquals("Instance of type org.kollektions.permutester.InterfaceMethodsReaderTest does not implement No.Such.Name", exception.message)
    }
}

private interface MandatoryRoverTests {
    fun canMove()
    fun canStop()
}