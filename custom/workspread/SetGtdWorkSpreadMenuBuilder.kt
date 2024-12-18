package org.freeplane.features.custom.workspread

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.custom.map.extensions.TreeSpread
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.GtdNum

const val SET_GTD_WORK_SPREAD_MENU_BUILDER = "SET_GTD_WORK_SPREAD_MENU_BUILDER"

object SetGtdWorkSpreadMenuBuilder : EntryVisitor {

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val actions: MutableCollection<AFreeplaneAction> = arrayListOf()

        TreeSpread.values().forEach {
            actions.add(SetGtdWorkSpreadAction(it))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}