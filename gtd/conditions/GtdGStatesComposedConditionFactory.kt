package org.freeplane.features.gtd.conditions

import org.freeplane.features.gtd.data.elements.GtdStatus

class GtdGStatesComposedConditionFactory(val statusesConditionFactory : GtdGStateConditionFactory) {
    fun create() : GtdGStatesComposedCondition {
        val rawConditions = GtdStatus.values.map { it to statusesConditionFactory(it)!! }.toMap()
        return GtdGStatesComposedCondition(rawConditions)
    }

    operator fun invoke() = create()
}