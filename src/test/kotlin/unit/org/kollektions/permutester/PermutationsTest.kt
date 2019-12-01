package org.kollektions.permutester

import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PermutationsTest {
    @Test
    fun worksForOneArgument(){
        val actual = Permutations(listOf("Red", "Blue", "White")).expectedPermutations
        assertEquals(setOf(listOf("Red"), listOf("Blue"), listOf("White")), actual.toSet())
    }

    @Test
    fun handlesEmptyParams(){
        assertFailsWith(IllegalArgumentException::class) {
            permutate()
        }
    }

    @Test
    fun `detects empty sublist`() {
        assertFailsWith(IllegalArgumentException::class) {
            permutate(listOf("Amber", "Green"), listOf())
        }
    }

    @Test
    fun worksForTwoArguments(){
        val actual = Permutations(listOf("Red", "Blue"),
                listOf("Jedi", "Elf")).expectedPermutations
        assertEquals(setOf(listOf("Red", "Jedi"), listOf("Blue", "Jedi"),
                listOf("Red", "Elf"), listOf("Blue", "Elf")), actual.toSet())
    }

    @Test
    fun worksForThreeArguments(){
        val actual = Permutations(listOf("Red", "Blue"),
                listOf("Jedi", "Elf"), listOf(BigDecimal.ZERO, BigDecimal.ONE)).expectedPermutations
        assertEquals(setOf(
                listOf("Red", "Jedi", BigDecimal.ZERO),
                listOf("Blue", "Jedi", BigDecimal.ZERO),
                listOf("Red", "Elf", BigDecimal.ZERO),
                listOf("Blue", "Elf", BigDecimal.ZERO),
                listOf("Red", "Jedi", BigDecimal.ONE),
                listOf("Blue", "Jedi", BigDecimal.ONE),
                listOf("Red", "Elf", BigDecimal.ONE),
                listOf("Blue", "Elf", BigDecimal.ONE)
        ), actual.toSet())
    }
}