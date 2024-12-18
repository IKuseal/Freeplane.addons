package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.GtdNum

const val SET_GTD_STATE_NUM_MENU_BUILDER = "SET_GTD_STATE_NUM_MENU_BUILDER"

object SetGtdStateNumMenuBuilder : EntryVisitor {

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val actions: MutableCollection<AFreeplaneAction> = arrayListOf()

        GtdNum.values().forEach {
            actions.add(SetGtdStateNumAction(it))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}