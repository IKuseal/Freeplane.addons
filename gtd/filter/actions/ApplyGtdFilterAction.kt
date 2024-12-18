package org.freeplane.features.gtd.filter.actions

import org.freeplane.features.custom.frcondition.ConditionCooperationType
import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.filter.GtdFilterController
import java.awt.event.ActionEvent

class ApplyGtdFilterAction(private val cooperationType: ConditionCooperationType)
    : AGtdAction(generateKey(cooperationType))
{
    override fun actionPerformed(e: ActionEvent?) {
        GtdFilterController.applyFilter(cooperationType)
    }

    companion object {
        fun generateKey(cooperationType: ConditionCooperationType) =
            "Gtd.${ApplyGtdFilterAction::class.simpleName}.${cooperationType.name}"
    }
}