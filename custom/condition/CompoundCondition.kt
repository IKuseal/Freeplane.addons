package org.freeplane.features.custom.condition

abstract class CompoundCondition<T>(val conditions : Collection<Condition<T>>) : Condition<T>()