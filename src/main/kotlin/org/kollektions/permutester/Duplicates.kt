package org.kollektions.permutester

fun assertNoDuplicates(testedPermutations: List<List<Any>>){
    val duplicates = findDuplicates(testedPermutations)
    assert(duplicates.isEmpty()) {
        val accountForPossiblePlural = if (duplicates.size > 1) "s" else ""
        with(StringBuilder("The following combination$accountForPossiblePlural tested more than once:")) {
            duplicates.forEach { append("\n"); append(it.toString()) }
            toString()
        }
    }
}

fun findDuplicates(items: List<List<Any>>): Set<List<Any>>{
    val groups = items.groupBy { it }
    return groups.filter { it.value.size > 1 }. map { it.key }. toSet()
}