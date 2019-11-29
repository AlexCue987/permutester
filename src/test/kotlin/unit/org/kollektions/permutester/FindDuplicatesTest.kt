package org.kollektions.permutester

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FindDuplicatesTest {
    @Test
    fun noDuplicates(){
        val actual = findDuplicates(listOf(listOf(1, 2), listOf(2, 3)))
        assertTrue(actual.isEmpty())
    }

    @Test
    fun duplicates(){
        val duplicateItem = listOf(2, 3)
        val actual = findDuplicates(listOf(listOf(1, 2), duplicateItem, listOf(3, 4), duplicateItem))
        val expected = setOf(duplicateItem)
        assertEquals(expected, actual)
    }
}