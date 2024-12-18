package org.freeplane.features.gtd.filter.modulefilter

import org.freeplane.features.filter.condition.ASelectableCondition
import org.freeplane.features.gtd.filter.AGtdSubFilterData
import org.freeplane.features.gtd.module.GtdModuleCondition

class GtdModuleFilterData : AGtdSubFilterData() {
    var isActivated : Boolean = false

    override val condition: ASelectableCondition?
        get() = if(isActivated) GtdModuleFilterData.condition else null

    companion object {
        val condition = GtdModuleCondition()
    }
}