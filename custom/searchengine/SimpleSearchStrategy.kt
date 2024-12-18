package org.freeplane.features.custom.searchengine

class SimpleSearchStrategy(pattern : String) : SearchStrategy(pattern) {
    private val predicate = pattern.lowercase().toPattern().asPredicate()

    private val String.formatted : String
        get() =
            if(isEmpty()) ".*"
            else ".*$this.*"

    override fun test(index: String) = predicate.test(index.lowercase())
}