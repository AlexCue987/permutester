package org.kollektions.permutester

import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream
import kotlin.test.Test

class ParameterizedExampleTest {

    @ParameterizedTest
    @MethodSource("validTokenProvider")
//    @ValueSource(ints = [3, 0])
    fun `parse valid tokens`(data: Int) {
        println(data)
    }

    @Test
    fun runMe() {
        println("runMe")
    }

    companion object {
        @JvmStatic
        private fun validTokenProvider() = Stream.of(1, 2, 3)
    }
}

