package org.freeplane.features.gtd.filter.statefilter

import org.freeplane.features.gtd.conditions.GtdGStateConditionFactory
import org.freeplane.features.gtd.conditions.GtdGStatesComposedConditionFactory
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.filter.AGtdSubFilterController
import org.freeplane.features.gtd.filter.statefilter.actions.GtdStatesFilterResetAction
import org.freeplane.features.gtd.filter.statefilter.actions.GtdStatesFilterTemplateMenuBuilder
import org.freeplane.features.gtd.filter.statefilter.actions.GtdStatesFilterTemplateMenuBuilder.GTD_STATES_FILTER_TEMPLATE_MENU_BUILDER
import org.freeplane.features.gtd.filter.statefilter.actions.ShowGtdStatesFilterDialogAction
import org.freeplane.features.gtd.filter.statefilter.actions.SwitchGtdStatesFilterAccumulationModeAction

object GtdStatesFilterController : AGtdSubFilterController() {
    fun init() {
        GtdController.run {
            addAction(ShowGtdStatesFilterDialogAction())
            addAction(GtdStatesFilterResetAction())
            addUiBuilder(GTD_STATES_FILTER_TEMPLATE_MENU_BUILDER, GtdStatesFilterTemplateMenuBuilder)
            addAction(SwitchGtdStatesFilterAccumulationModeAction())
        }
    }

    override val filterData: GtdStatesFilterData?
        get() = super.filterData as GtdStatesFilterData?

    override fun createFilterData() = super.createFilterData() as GtdStatesFilterData

    fun createConditionsData() = createFilterData().conditionsData

    override fun _createFilterData() =
        GtdStatesFilterData(GtdGStatesComposedConditionFactory(GtdGStateConditionFactory())())

    fun reset() {
        filterData?.conditionsData?.reset()
        onFilterDataUpdated()
    }
}


