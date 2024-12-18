package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.GtdSubStateClass

const val SWITCH_GTD_SUB_STATE_MENU_BUILDER = "SWITCH_GTD_SUB_STATE_MENU_BUILDER"

object SwitchGtdSubStateMenuBuilder : EntryVisitor {

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val actions: MutableCollection<AFreeplaneAction> = arrayListOf()

        GtdSubStateClass.values().forEach {
            actions.add(SwitchGtdSubStateAction(it.default))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}