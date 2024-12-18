package org.freeplane.features.gtd.filter

import org.freeplane.features.custom.frcondition.SimpleConditionWrapper
import org.freeplane.features.filter.condition.ASelectableCondition

class GtdFilterData {
    // subFilters **************************************************************************
    val subFiltersData = hashMapOf<Any, AGtdSubFilterData>()

    operator fun get(key: Any) = subFiltersData[key]

    operator fun set(key: Any, value: AGtdSubFilterData) {
        subFiltersData[key] = value
    }

    // condition ***************************************************************************
    var conditionWrapper: SimpleConditionWrapper? = null
        private set

    var condition: ASelectableCondition?
        get() = conditionWrapper?.condition
        set(value) {
            if (value == null) {
                conditionWrapper = null
            } else {
                conditionWrapper?.let { it.condition = value }
                    ?: SimpleConditionWrapper(value).also { conditionWrapper = it }
            }
        }

    val isFilteringSet get() = conditionWrapper != null

    // element conditions *******************************************************************
    val elementConditions = hashMapOf<String, ASelectableCondition>()
}