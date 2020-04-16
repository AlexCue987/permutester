package org.kollektions.permutester

import java.lang.IllegalArgumentException
import kotlin.reflect.full.allSupertypes
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.jvm.jvmName
import kotlin.test.Test

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

fun methodIsTest(instance: Any, methodName: String): Boolean {
    val function = instance::class.members.asSequence()
        .filter { it.name == methodName }
        .toList()

    if(function.isEmpty() || function[0].annotations.isEmpty()) {
        return false
    }

    return function[0].annotations.asSequence()
        .filter { it.annotationClass.jvmName == "org.junit.Test" }
        .any()
}

fun allInterfaceMethodsAreTests(instance: Any, interfaceName: String): Boolean {
    val methodNames = getInterfaceMethodNames(instance, interfaceName)
    return methodNames.all { methodIsTest(instance, it) }
}