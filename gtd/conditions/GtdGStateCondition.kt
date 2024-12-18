package org.freeplane.features.gtd.conditions

import org.freeplane.features.custom.inverse
import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.filter.condition.ConjunctConditions
import org.freeplane.features.gtd.conditions.base.GtdStateCondition
import org.freeplane.features.gtd.conditions.base.GtdSubStateCondition
import org.freeplane.features.gtd.conditions.base.ContainsRequirement
import org.freeplane.features.gtd.conditions.base.ContainsRequirement.*
import org.freeplane.features.gtd.data.elements.GtdState
import org.freeplane.features.gtd.data.elements.GtdStatus
import org.freeplane.features.gtd.data.elements.GtdSubState
import java.rmi.UnexpectedException

open class GtdGStateCondition(val status : GtdStatus, val limitingSubStates : Collection<GtdSubState>) {
    private val limitings = hashMapOf<GtdSubState, ContainsRequirement>().apply {
        limitingSubStates.forEach { put(it, NO_MATTER) }
    }

    fun setLimiting(subState : GtdSubState, limiting : ContainsRequirement) {
        limitings[subState] = limiting
    }

    fun getLimiting(subState: GtdSubState) = limitings[subState]

    fun containsLimiting(subState: GtdSubState) = limitings[subState] != null

    operator fun invoke() = generateCondition()

    private fun generateCondition() : ASelectableCondition {
        val conditions = arrayListOf<ASelectableCondition>()
        conditions.add(status.mainCondition)
        limitingSubStates.forEach {
            it.limitingCondition?.let { conditions.add(it) }
        }

        return ConjunctConditions.combine(*conditions.toTypedArray())
    }

    private val GtdStatus.mainCondition get() : ASelectableCondition = when(this) {
        is GtdState -> GtdStateCondition(this)
        is GtdSubState -> GtdSubStateCondition(this)
        else -> throw UnexpectedException("only GtdState or GtdSubState expected")
    }

    private val GtdSubState.limitingCondition get() : ASelectableCondition? = when(limitings[this]) {
        NO_MATTER -> null
        INCLUDE -> GtdSubStateCondition(this)
        EXCLUDE -> GtdSubStateCondition(this).inverse
        else -> null
    }

    fun reset() {
        limitingSubStates.forEach { limitings[it] = NO_MATTER }
    }
}