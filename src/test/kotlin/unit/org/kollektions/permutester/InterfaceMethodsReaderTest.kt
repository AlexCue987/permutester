package org.kollektions.permutester

import java.lang.IllegalArgumentException
import kotlin.test.*

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

    @Test
    fun `detects that method is a Test`() {
        assertTrue(methodIsTest(this, "handles wrong interface name"))
    }

    @Test
    fun `detects that method is not a Test`() {
        assertFalse(methodIsTest(this, "canStop"))
    }

    @Test
    fun `this class does not implement all interface methods with tests`() {
        assertFalse { allInterfaceMethodsAreTests(this, "org.kollektions.permutester.MandatoryRoverTests") }
    }
}

class CompleteMandatoryRoverTest: MandatoryRoverTests {
    @Test
    override fun canMove() {
        assertEquals(4, 2*2)
    }

    @Test
    override fun canStop() {
        assertEquals(4, 2*2)
    }

    @Test
    fun `this class does implement all interface methods with tests`() {
        assertTrue { allInterfaceMethodsAreTests(this, "org.kollektions.permutester.MandatoryRoverTests") }
    }
}

class IncompleteMandatoryRoverTest: MandatoryRoverTests {
    //@Test - missing annotation
    override fun canMove() {
        assertEquals(4, 2*2)
    }

    @Test
    override fun canStop() {
        assertEquals(4, 2*2)
    }

    @Test
    fun `this class does not implement every interface method with tests`() {
        assertFalse { allInterfaceMethodsAreTests(this, "org.kollektions.permutester.MandatoryRoverTests") }
    }
}

private interface MandatoryRoverTests {
    fun canMove()
    fun canStop()
}