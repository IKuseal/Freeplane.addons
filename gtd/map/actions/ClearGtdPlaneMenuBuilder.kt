package org.freeplane.features.gtd.map.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.tag.extWorkBuiltinPlanes

object ClearGtdPlaneMenuBuilder : EntryVisitor {

    const val key = "CLEAR_GTD_PLANE_MENU_BUILDER"

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val actions: MutableCollection<AFreeplaneAction> = arrayListOf()

        extWorkBuiltinPlanes
            .forEach {
                actions.add(ClearGtdPlaneAction(it))
            }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}