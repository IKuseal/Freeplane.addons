package org.freeplane.features.custom.condition

abstract class Condition<T> {
    abstract fun check(value : T) : Boolean
}

val <T> Condition<T>.not get() = InvertedCondition(this)