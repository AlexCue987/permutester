package org.kollektions.permutester

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestSuiteWithAllCases {
    @Test
    @SampleTestCase(color = "Red", shape = "Dot")
    fun `works for red dot`() {}

    @Test
    @SampleTestCase(color = "Green", shape = "Dot")
    fun `works for green dot`() {}

    @Test
    @SampleTestCase(color = "Red", shape = "Comma")
    fun `works for red comma`() {}

    @Test
    @SampleTestCase(color = "Green", shape = "Comma")
    fun `works for green comma`() {}

    @Test
    fun `works for test suite with all cases`() {
        val actualPermutations = getMembersAnnotationValues<SampleTestCase>(this) { listOf(it.color, it.shape) }
        expectedPermutations.match(actualPermutations)
    }
}

class TestSuiteWithAllCasesButOneIgnored {
    @Test
    @SampleTestCase(color = "Red", shape = "Dot")
    fun `works for red dot`() {}

    @Ignore
    @Test
    @SampleTestCase(color = "Green", shape = "Dot")
    fun `works for green dot`() {}

    @Test
    @SampleTestCase(color = "Red", shape = "Comma")
    fun `works for red comma`() {}

    @Test
    @SampleTestCase(color = "Green", shape = "Comma")
    fun `works for green comma`() {}

    @Test
    fun `finds ignored test`() {
        val actualPermutations = getMembersAnnotationValues<SampleTestCase>(this) { listOf(it.color, it.shape) }
        val actual = assertFailsWith<AssertionError> { expectedPermutations.match(actualPermutations) }
        assertEquals("Missing:\n[Green, Dot]", actual.message)
    }
}

class TestSuiteWithAllCasesAndDuplicate {
    @Test
    @SampleTestCase(color = "Red", shape = "Dot")
    fun `works for red dot`() {}

    @Test
    @SampleTestCase(color = "Green", shape = "Dot")
    fun `works for green dot`() {}

    @Test
    @SampleTestCase(color = "Red", shape = "Comma")
    fun `works for red comma`() {}

    @Test
    @SampleTestCase(color = "Red", shape = "Comma")
    fun `works for red comma duplicate`() {}

    @Test
    @SampleTestCase(color = "Green", shape = "Comma")
    fun `works for green comma`() {}

    @Test
    fun `finds duplicate test`() {
        val actualPermutations = getMembersAnnotationValues<SampleTestCase>(this) { listOf(it.color, it.shape) }
        val actual = assertFailsWith<AssertionError> { expectedPermutations.match(actualPermutations) }
        assertEquals("Duplicates:\n[Red, Comma]", actual.message)
    }
}

class TestSuiteWithMissingTestForGreenDot {
    @Test
    @SampleTestCase(color = "Red", shape = "Dot")
    fun `works for red dot`() {}

    @SampleTestCase(color = "Green", shape = "Dot")
    fun `works for green dot`() {}

    @Test
    @SampleTestCase(color = "Red", shape = "Comma")
    fun `works for red comma`() {}

    @Test
    @SampleTestCase(color = "Green", shape = "Comma")
    fun `works for green comma`() {}

    @Test
    fun `finds missing test`() {
        val actualPermutations = getMembersAnnotationValues<SampleTestCase>(this) { listOf(it.color, it.shape) }
        val actual = assertFailsWith<AssertionError> { expectedPermutations.match(actualPermutations) }
        assertEquals("Missing:\n[Green, Dot]", actual.message)
    }
}

class TestSuiteWithUnexpectedTestForAmberDot {
    @Test
    @SampleTestCase(color = "Red", shape = "Dot")
    fun `works for red dot`() {}

    @Test
    @SampleTestCase(color = "Green", shape = "Dot")
    fun `works for green dot`() {}

    @Test
    @SampleTestCase(color = "Amber", shape = "Dot")
    fun `works for amber dot`() {}

    @Test
    @SampleTestCase(color = "Red", shape = "Comma")
    fun `works for red comma`() {}

    @Test
    @SampleTestCase(color = "Green", shape = "Comma")
    fun `works for green comma`() {}

    @Test
    fun `finds unexpected test`() {
        val actualPermutations = getMembersAnnotationValues<SampleTestCase>(this) { listOf(it.color, it.shape) }
        val actual = assertFailsWith<AssertionError> { expectedPermutations.match(actualPermutations) }
        assertEquals("Unexpected:\n[Amber, Dot]", actual.message)
    }
}

val expectedPermutations = Permutations(listOf("Red", "Green"), listOf("Dot", "Comma"))

private annotation class SampleTestCase(val color: String, val shape: String)
