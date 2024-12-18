package org.freeplane.features.gtd.filter.statefilter.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.gtd.conditions.templates.AGtdGStatesTemplate
import org.freeplane.features.gtd.conditions.templates.excludeCdGStatesTemplate
import org.freeplane.features.gtd.conditions.templates.excludeRvGStatesTemplate
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.filter.statefilter.*

object GtdStatesFilterTemplateMenuBuilder : EntryVisitor {
    const val GTD_STATES_FILTER_TEMPLATE_MENU_BUILDER = "GTD_STATES_FILTER_TEMPLATE_MENU_BUILDER"

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val newAction : (AGtdGStatesTemplate, String) -> GtdStatesFilterTemplateAction = { template, name ->
            val template = excludeRvGStatesTemplate(
                excludeCdGStatesTemplate(template))

            GtdStatesFilterTemplateAction(template, name)
        }

        val actions : MutableCollection<AFreeplaneAction> = arrayListOf<AFreeplaneAction>().apply {
            add(newAction(iGStatesTemplate(null), "I*"))

            add(newAction(l0GStatesTemplate(null), "L0"))
            add(newAction(l01GStatesTemplate(null), "L01"))
            add(newAction(l012GStatesTemplate(null), "L012"))
            add(newAction(l0123GStatesTemplate(null), "L0123"))

            add(newAction(q0GStatesTemplate(null), "Q0"))
            add(newAction(q01GStatesTemplate(null), "Q01"))
            add(newAction(q012GStatesTemplate(null), "Q012"))
            add(newAction(q0123GStatesTemplate(null), "Q0123"))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}