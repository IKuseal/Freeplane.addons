package org.freeplane.features.gtd.transparency.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.gtd.conditions.templates.*
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.transparency.classicalTransparencyTemplate
import org.freeplane.features.gtd.transparency.q5to9GStatesTemplate

object GtdTransparencyTemplateMenuBuilder : EntryVisitor {
    const val GTD_TRANSPARENCY_TEMPLATE_MENU_BUILDER = "GTD_TRANSPARENCY_TEMPLATE_MENU_BUILDER"

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val newAction : (AGtdGStatesTemplate, String) -> GtdTransparencyTemplateAction = { template, name ->
            val template = excludeRvGStatesTemplate(
                excludeCdGStatesTemplate(template))

            GtdTransparencyTemplateAction(template, name)
        }

        val actions : MutableCollection<AFreeplaneAction> = arrayListOf<AFreeplaneAction>().apply {
            add(newAction(classicalTransparencyTemplate, "Classical"))
            add(newAction(q1GStatesTemplate(null), "Q1*"))
            add(newAction(q2GStatesTemplate(null), "Q2*"))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}