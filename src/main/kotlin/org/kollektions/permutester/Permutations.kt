package org.kollektions.permutester

class Permutations(val expectedPermutations: List<List<Any>>) {
    constructor(vararg items: List<Any>): this(permutate(*items))

    fun match(actualPermutations: List<List<Any>>) {
        val duplicates = findDuplicates(actualPermutations)
        val missing = expectedPermutations.filter { it !in actualPermutations }
        val unexpected = actualPermutations.filter { it !in expectedPermutations }
        val possibleProblems = listOf<Pair<String, Collection<Any>>>(
            Pair("Duplicates", duplicates),
            Pair("Missing", missing),
            Pair("Unexpected", unexpected)
        )
        val problems = possibleProblems.filter { it.second.isNotEmpty() }
        if (problems.isNotEmpty()) {
            val message = problems.map { "${it.first}:\n${describeList(it.second)}" }.joinToString("\n")
            throw AssertionError(message)
        }
    }
}

private fun describeList(collection: Collection<Any>) = collection.joinToString("\n")

fun permutate(vararg items: List<Any>): List<List<Any>>{
    val itemsList = items.toList()
    require(itemsList.isNotEmpty()){
        "Must provide at least one argument"
    }
    itemsList.forEach {
        require(it.isNotEmpty()) { "Each argument must be not empty." }
    }
    val offset = itemsList.size - 1
    return permutateForOffset(offset, itemsList)
}

fun permutateForOffset(offset: Int, items: List<List<Any>>): List<List<Any>>{
    if(offset == 0){
        return items[0].map { listOf(it) }
    }
    val headPermutations = permutateForOffset(offset - 1, items)
    return crossJoin(headPermutations, items[offset])
}

fun crossJoin(headPermutations: List<List<Any>>, tails: List<Any>): List<List<Any>> {
    val ret: MutableList<List<Any>> = mutableListOf()
    for(headPermutation in headPermutations){
        for(tail in tails){
            val permutation = mutableListOf<Any>()
            permutation.addAll(headPermutation)
            permutation.add(tail)
            ret.add(permutation)
        }
    }
    return ret
}