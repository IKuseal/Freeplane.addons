package org.freeplane.features.gtd.filter.layersfilter

import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.filter.AGtdSubFilterController
import org.freeplane.features.gtd.filter.layersfilter.EnableGtdLayerLevelConditionMenuBuilder.ENABLE_GTD_LAYER_LEVEL_CONDITION_MENU_BUILDER

object GtdLayersFilterController : AGtdSubFilterController() {
    fun init() {
        GtdController.addUiBuilder(ENABLE_GTD_LAYER_LEVEL_CONDITION_MENU_BUILDER, EnableGtdLayerLevelConditionMenuBuilder)
    }

    override fun createFilterData(): GtdLayersFilterData =
        super.createFilterData() as GtdLayersFilterData

    override fun _createFilterData(): GtdLayersFilterData {
        return GtdLayersFilterData()
    }

    override val filterData: GtdLayersFilterData?
        get() = super.filterData as GtdLayersFilterData?

    fun enableLayerLevelCondition(level : Int, enable : Boolean = true) {
        createFilterData().enabledLayerLevels[level] = enable
        onFilterDataUpdated()
    }
}