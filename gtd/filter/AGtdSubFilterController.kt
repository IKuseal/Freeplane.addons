package org.freeplane.features.gtd.filter

import org.freeplane.features.filter.condition.ASelectableCondition

abstract class AGtdSubFilterController {
    // filter-data *****************************************************************************************
    protected val filterDataKey : Any
        get() = this::class

    open val filterData : AGtdSubFilterData?
        get() = GtdFilterController.filterData[filterDataKey]

    open fun createFilterData() : AGtdSubFilterData {
        return filterData ?: _createFilterData().also {
            GtdFilterController.filterData[filterDataKey] = it }
    }

    protected abstract fun _createFilterData() : AGtdSubFilterData

    // miscellaneous ********************************************************************************************
    val condition : ASelectableCondition?
        get() = filterData?.condition

    fun onFilterDataUpdated() {
        GtdFilterController.onIntegralConditionChanged()
    }
}