package org.freeplane.features.gtd.filter.modulefilter

import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.filter.AGtdSubFilterController

object GtdModuleFilterController : AGtdSubFilterController() {
    fun init() {
        GtdController.addAction(SwitchGtdModuleFiltering())
    }

    override fun createFilterData(): GtdModuleFilterData =
        super.createFilterData() as GtdModuleFilterData

    override fun _createFilterData(): GtdModuleFilterData {
        return GtdModuleFilterData()
    }

    override val filterData: GtdModuleFilterData?
        get() = super.filterData as GtdModuleFilterData?

    fun enableFiltering(enable : Boolean = true) {
        createFilterData().isActivated = enable
        onFilterDataUpdated()
    }
}