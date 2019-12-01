package org.kollektions.permutester

import kotlin.reflect.full.allSupertypes
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.jvmErasure
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.Test

class ReflectorTest: MandatoryTests {
    @Test
    override fun worksForOneArgument(){
        val actual = Permutations(listOf("Red", "Blue", "White")).expectedPermutations
        assertEquals(setOf(listOf("Red"), listOf("Blue"), listOf("White")), actual.toSet())
    }

    @Test
    override fun handlesEmptyParams(){
        assertFailsWith(IllegalArgumentException::class) {
            permutate()
        }
    }

    @Test
    fun reflect() {
        val functions = (this as MandatoryTests)::class.declaredMemberFunctions
        functions.forEach {
            println(it)
        }
        val supertypes = this::class.allSupertypes
            .filter { it.jvmErasure.java.name != "java.lang.Object" }
//        val names = supertypes.map{ it.jvmErasure.java.name }
//        for (name in names) {
//            println(name)
//        }
        supertypes.forEach {
//            println(it)
//            if(it.javaType.typeName == "org.kollektions.permutester.MandatoryTests") {
                println("Type: ${it.javaType.typeName}")
                val members = it.jvmErasure.members.filter { member -> member.name !in listOf("equals", "hashCode", "toString") }
                members.forEach{
                        member -> println("Member: ${member.name}")
                }
//            }
        }
    }
}

interface MandatoryTests{
    fun handlesEmptyParams()
    fun worksForOneArgument()
}