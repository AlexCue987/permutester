package org.kollektions.permutester

import java.lang.IllegalArgumentException
import kotlin.reflect.full.allSupertypes
import kotlin.reflect.jvm.jvmErasure

fun getInterfaceMethodNames(instance: Any, interfaceName: String): List<String> {
     val implementation = instance::class.allSupertypes
        .find { it.jvmErasure.java.name == interfaceName }
        ?: throw IllegalArgumentException("Instance of type ${instance::class.qualifiedName} does not implement $interfaceName")

    val methodNames = implementation.jvmErasure.members
        .asSequence()
        .filter { member -> member.name !in listOf("equals", "hashCode", "toString") }
        .map { it.name }
        .toList()

    return methodNames
}

