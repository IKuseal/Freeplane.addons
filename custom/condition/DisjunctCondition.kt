package org.freeplane.features.custom.condition

class DisjunctCondition<T>(conditions : Collection<Condition<T>>) : CompoundCondition<T>(conditions) {
    override fun check(value: T): Boolean =
        conditions.any { it.check(value) }
}

val <T> Collection<Condition<T>>.disjunctCondition get() = DisjunctCondition(this)