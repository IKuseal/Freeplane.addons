package org.freeplane.features.custom.searchengine

class SpaceBasedSearchStrategy(pattern : String) : SearchStrategy(pattern) {
    private val predicate = pattern.formatted.toPattern().asPredicate()

    private val String.formatted : String
        get() =
            if(isEmpty()) ".*"
            else split(" ").joinToString(".* .*", "(?i).*", ".*")

    override fun test(index: String) = predicate.test(index)
}