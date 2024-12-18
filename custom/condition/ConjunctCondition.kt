package org.freeplane.features.custom.condition

class ConjunctCondition<T>(conditions : Collection<Condition<T>>) : CompoundCondition<T>(conditions) {
    override fun check(value: T): Boolean =
        conditions.all { it.check(value) }
}

val <T> Collection<Condition<T>>.conjunctCondition get() = ConjunctCondition(this)