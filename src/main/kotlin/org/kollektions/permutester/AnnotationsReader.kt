package org.kollektions.permutester

import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.test.Ignore
import kotlin.test.Test

inline fun<reified T: Annotation> getMembersAnnotationValues(instance: Any, mapper: (t: T) -> List<Any>): List<List<Any>>{
    val functions = instance::class.declaredMemberFunctions
    val annotationsOnTests = functions
        .filter { it.findAnnotation<Test>() != null }
        .filter { it.findAnnotation<Ignore>() == null }
        .map { it.findAnnotation<T>() }
        .filterNot { it == null }
    return annotationsOnTests
        .map { mapper(it!!) }
}
