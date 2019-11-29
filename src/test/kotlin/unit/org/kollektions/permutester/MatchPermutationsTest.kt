package org.kollektions.permutester

import kotlin.test.Test
import kotlin.AssertionError
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MatchPermutationsTest {
    @Test
    fun `matchPermutations passes`() {
        val permutations = listOf(listOf("Red", "Dot"), listOf("Blue", "Comma"))
        matchPermutations(expected = permutations, actual = permutations)
    }

    @Test
    fun `matchPermutations finds duplicate`() {
        val duplicateItem = listOf("Red", "Dot")
        val expected = listOf(duplicateItem, listOf("Blue", "Comma"))
        val actual = listOf(duplicateItem, duplicateItem, listOf("Blue", "Comma"))
        val ex = assertFailsWith<AssertionError> { matchPermutations(expected, actual) }
        assertEquals("Duplicates:\n[Red, Dot]", ex.message)
    }

    @Test
    fun `matchPermutations finds missing case`() {
        val missingItem = listOf("Red", "Dot")
        val expected = listOf(missingItem, listOf("Blue", "Comma"))
        val actual = listOf(listOf("Blue", "Comma"))
        val ex = assertFailsWith<AssertionError> { matchPermutations(expected, actual) }
        assertEquals("Missing:\n[Red, Dot]", ex.message)
    }

    @Test
    fun `matchPermutations finds unexpected case`() {
        val duplicateItem = listOf("Red", "Dot")
        val expected = listOf(listOf("Red", "Dot"), listOf("Blue", "Comma"))
        val unexpected = listOf("Red", "Comma")
        val actual = listOf(listOf("Red", "Dot"), unexpected, listOf("Blue", "Comma"))
        val ex = assertFailsWith<AssertionError> { matchPermutations(expected, actual) }
        assertEquals("Unexpected:\n[Red, Comma]", ex.message)
    }

    @Test
    fun `matchPermutations finds all kinds of problems`() {
        val duplicateItem = listOf("Red", "Dot")
        val missingItem = listOf("Blue", "Comma")
        val matchedItem = listOf("Amber", "Square")
        val expected = listOf(duplicateItem, missingItem, matchedItem)
        val unexpected = listOf("Red", "Comma")
        val actual = listOf(duplicateItem, duplicateItem, unexpected, matchedItem)
        val ex = assertFailsWith<AssertionError> { matchPermutations(expected, actual) }
        assertEquals("Duplicates:\n[Red, Dot]\nMissing:\n[Blue, Comma]\nUnexpected:\n[Red, Comma]", ex.message)
    }
}