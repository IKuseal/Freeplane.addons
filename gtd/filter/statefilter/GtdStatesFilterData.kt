package org.freeplane.features.gtd.filter.statefilter

import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.gtd.conditions.GtdGStatesComposedCondition
import org.freeplane.features.gtd.filter.AGtdSubFilterData

class GtdStatesFilterData(val conditionsData : GtdGStatesComposedCondition) : AGtdSubFilterData() {
    override val condition: ASelectableCondition? get() = conditionsData()

    var isAccumulationMode = true
}