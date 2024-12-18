package org.freeplane.features.custom.condition

class InvertedCondition<T>(condition : Condition<T>) : CompoundCondition<T>(listOf(condition)) {
    val condition = conditions.first()

    override fun check(value: T): Boolean = !condition.check(value)
}