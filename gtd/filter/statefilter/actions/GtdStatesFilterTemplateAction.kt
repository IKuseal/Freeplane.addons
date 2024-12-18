package org.freeplane.features.gtd.filter.statefilter.actions

import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.conditions.templates.AGtdGStatesTemplate
import org.freeplane.features.gtd.filter.statefilter.GtdStatesFilterController
import java.awt.event.ActionEvent

class GtdStatesFilterTemplateAction(val template : AGtdGStatesTemplate, val name : String) :
    AGtdAction("${GtdStatesFilterTemplateAction::class.simpleName}.$name", name)
{
    override fun actionPerformed(e: ActionEvent?) {
        GtdStatesFilterController.run {
            if(!createFilterData().isAccumulationMode)
                reset()
            template.switch(createConditionsData())
            onFilterDataUpdated()
        }
    }
}