package org.kollektions.permutester

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.lang.AssertionError
import java.util.stream.Stream
import kotlin.streams.toList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ParameterizedExampleTest {

    @ParameterizedTest
    @MethodSource("completeSetOfCases")
    fun `parse valid tokens`(data: ParameterizedExampleTestCase) {
        println(data)
    }

    @Test
    fun `passes when all permutations tested`() {
        val parameterizedPermutations = completeSetOfCases()
            .map { it.mapToPermutation() }
            .toList()
        allExpectedTestCases.match(parameterizedPermutations)
    }

    @Test
    fun `detect problem`() {
        val parameterizedPermutations = completeSetOfCasesWithDuplicate()
            .map { it.mapToPermutation() }
            .toList()
        val thrownException = assertFailsWith<AssertionError>
            { allExpectedTestCases.match(parameterizedPermutations) }
        assertEquals("Duplicates:\n[Red, Dot]", thrownException.message)
    }

    private val allColors = listOf("Red", "Amber")
    private val allShapes = listOf("Comma", "Dot")

    private val allExpectedTestCases = Permutator(allColors, allShapes)

    data class ParameterizedExampleTestCase(val color: String, val shape: String) {
        fun mapToPermutation() = listOf(color, shape)
    }

    companion object {
        @JvmStatic
        private fun completeSetOfCases() = Stream.of(
            ParameterizedExampleTestCase("Red", "Dot"),
            ParameterizedExampleTestCase("Red", "Comma"),
            ParameterizedExampleTestCase("Amber", "Dot"),
            ParameterizedExampleTestCase("Amber", "Comma")
        )

        @JvmStatic
        private fun completeSetOfCasesWithDuplicate() = Stream.of(
            ParameterizedExampleTestCase("Red", "Dot"),
            ParameterizedExampleTestCase("Red", "Dot"),
            ParameterizedExampleTestCase("Red", "Comma"),
            ParameterizedExampleTestCase("Amber", "Dot"),
            ParameterizedExampleTestCase("Amber", "Comma")
        )
    }
}

