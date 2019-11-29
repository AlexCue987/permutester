package org.kollektions.permutester

import kotlin.test.Test
import kotlin.test.assertFailsWith

class AssertNoDuplicatesTest {
    @Test
    fun noDuplicates(){
        assertNoDuplicates(listOf(listOf(1, 2), listOf(2, 3)))
    }

    @Test
    fun duplicates(){
        val duplicateItem = listOf(2, 3)
        assertFailsWith<AssertionError> {  assertNoDuplicates(listOf(listOf(1, 2), duplicateItem, listOf(3, 4), duplicateItem)) }
    }
}