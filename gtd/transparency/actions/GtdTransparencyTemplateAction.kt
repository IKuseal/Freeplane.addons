package org.freeplane.features.gtd.transparency.actions

import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.conditions.templates.AGtdGStatesTemplate
import org.freeplane.features.gtd.transparency.GtdTransparencyController
import java.awt.event.ActionEvent

class GtdTransparencyTemplateAction(val template : AGtdGStatesTemplate, val name : String) :
    AGtdAction("${GtdTransparencyTemplateAction::class.simpleName}.$name", name)
{
    override fun actionPerformed(e: ActionEvent?) {
        GtdTransparencyController.run {
            if(!createTransparencyData().isAccumulationMode) reset()
            template.switch(transparencyConditionData())
            updateTransparency()
        }
    }
}