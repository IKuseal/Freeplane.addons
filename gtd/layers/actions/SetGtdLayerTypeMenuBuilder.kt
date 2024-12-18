package org.freeplane.features.gtd.layers.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.layers.GtdLayerType


object SetGtdLayerTypeMenuBuilder : EntryVisitor {
    const val SET_GTD_LAYER_TYPE_MENU_BUILDER = "SET_GTD_LAYER_TYPE_MENU_BUILDER"

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val actions: MutableCollection<AFreeplaneAction> = arrayListOf()

        GtdLayerType.values().forEach {
            actions.add(SetGtdLayerTypeAction(it))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}