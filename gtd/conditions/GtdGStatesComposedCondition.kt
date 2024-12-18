package org.freeplane.features.gtd.conditions

import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.filter.condition.DisjunctConditions
import org.freeplane.features.gtd.conditions.base.ContainsRequirement
import org.freeplane.features.gtd.data.elements.GtdStatus
import org.freeplane.features.gtd.data.elements.GtdSubState

class GtdGStatesComposedCondition(
    private val rawConditionByStatus : Map<GtdStatus, GtdGStateCondition>)
{
    private val enabledByStatus = hashMapOf<GtdStatus, Boolean>().apply {
        rawConditionByStatus.keys.forEach {
            this[it] = false
        }
    }

    // statuses ****************************************************************************
    val statuses : Set<GtdStatus> get() = enabledByStatus.keys

    fun containsStatus(status: GtdStatus) = enabledByStatus.contains(status)

    // enable ******************************************************************************
    fun enableStatus(status : GtdStatus, enabled : Boolean = true) {
        enabledByStatus[status] = enabled
    }

    fun isStatusEnabled(status: GtdStatus) = enabledByStatus[status]!!

    val enabledStatuses : Collection<GtdStatus> get() =
        enabledByStatus.filterValues { it }.keys

    // raw condition ************************************************************************
    fun getRawCondition(status: GtdStatus) = rawConditionByStatus[status]!!

    val rawConditions get() = rawConditionByStatus.values

    val enabledRawConditions : Sequence<GtdGStateCondition> get() = _enabledRawConditions

    val _enabledRawConditions : Sequence<GtdGStateCondition> get() =
        enabledByStatus.asSequence()
            .filter { (_, enabled) -> enabled }
            .map { (status, _) -> getRawCondition(status) }

    // element condition *******************************************************************
    private fun conditionOf(status: GtdStatus) : ASelectableCondition? =
        if(isStatusEnabled(status)) _conditionOf(status) else null

    private fun _conditionOf(status : GtdStatus) : ASelectableCondition =
        rawConditionByStatus[status]!!()

    private val conditions get() = _enabledRawConditions.map { it() }

    // condition *************************************************************************
    operator fun invoke() = condition

    private val condition : ASelectableCondition? get() {
        val conditions = this.conditions.toList().takeIf { it.isNotEmpty() } ?: return null

        return DisjunctConditions.combine(*conditions.toTypedArray())
    }

    // limitings *********************************************************************************
    fun setLimiting(subStateLimiting : Pair<GtdSubState, ContainsRequirement>) {
        val (subState, limiting) = subStateLimiting
        rawConditionByStatus.values.forEach {
            if(!it.containsLimiting(subState)) return@forEach
            it.setLimiting(subState, limiting)
        }
    }

    // heap **************************************************************************************
    fun reset() {
        statuses.forEach { enabledByStatus[it] = false }
        rawConditions.forEach { it.reset() }
    }
}

fun GtdGStatesComposedCondition.statusesWithLimiting
            (subState : GtdSubState, limiting : ContainsRequirement) =
    rawConditions.filter { it.getLimiting(subState) == limiting }.map { it.status }