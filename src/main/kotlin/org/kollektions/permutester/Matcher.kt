package org.kollektions.permutester

fun matchPermutations(expected: List<List<Any>>,
                      actual : List<List<Any>>) {
    val duplicates = findDuplicates(actual)
    val missing = expected.filter { it !in actual }
    val unexpected = actual.filter { it !in expected }
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

private fun describeList(collection: Collection<Any>) = collection.joinToString("\n")

