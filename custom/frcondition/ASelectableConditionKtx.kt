package org.freeplane.features.custom.frcondition

import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.filter.condition.ConjunctConditions
import org.freeplane.features.filter.condition.DisjunctConditions
import org.freeplane.features.filter.condition.ICombinedCondition

fun ASelectableCondition.containsReferential(condition: ASelectableCondition) : Boolean {
    val iterator = iterator()

    while(iterator.hasNext())
        if(iterator.next() === condition) return true

    return false
}

operator fun ASelectableCondition.iterator() : Iterator<ASelectableCondition> =
    if(this !is ICombinedCondition) arrayListOf(this).iterator()
    else (this as ICombinedCondition).iterator()

operator fun ICombinedCondition.iterator(): Iterator<ASelectableCondition> {
    val allIterator : Iterator<ASelectableCondition> = split().iterator()

    return object : Iterator<ASelectableCondition> {
        var iterator = allIterator.next().iterator()

        override fun hasNext() =
            iterator.hasNext() || allIterator.hasNext()

        override fun next(): ASelectableCondition =
            if(iterator.hasNext()) iterator.next()
            else {
                iterator = allIterator.next().iterator()
                iterator.next()
            }
    }
}

fun conjunctCondition(conditions: Collection<ASelectableCondition>) =
    ConjunctConditions.combine(*conditions.toTypedArray())!!

fun disjunctCondition(conditions: Collection<ASelectableCondition>) =
    DisjunctConditions.combine(*conditions.toTypedArray())!!