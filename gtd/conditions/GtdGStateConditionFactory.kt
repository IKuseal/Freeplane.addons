package org.freeplane.features.gtd.conditions

import org.freeplane.features.gtd.data.elements.GtdStatus
import org.freeplane.features.gtd.data.elements.GtdSubState

class GtdGStateConditionFactory : IGtdGStateConditionFactory {
    private val standardSubStatesPack : Collection<GtdSubState> = GtdSubState.values

    val subStatePackByStatus = hashMapOf<GtdStatus, Collection<GtdSubState>>().apply {
        GtdStatus.values.forEach {
            this[it] = ArrayList(standardSubStatesPack).apply { remove(it) }
        }
    }

    override fun create(status: GtdStatus): GtdGStateCondition? {
        val subStates = subStatePackByStatus[status] ?: standardSubStatesPack
        return GtdGStateCondition(status, subStates)
    }

    operator fun invoke(status : GtdStatus) = create(status)
}