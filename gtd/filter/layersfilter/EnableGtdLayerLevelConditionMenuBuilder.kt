package org.freeplane.features.gtd.filter.layersfilter

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.layers.MAX_LAYER_LEVEL

object EnableGtdLayerLevelConditionMenuBuilder : EntryVisitor {
    const val ENABLE_GTD_LAYER_LEVEL_CONDITION_MENU_BUILDER = "ENABLE_GTD_LAYER_LEVEL_CONDITION_MENU_BUILDER"

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val actions: MutableCollection<AFreeplaneAction> = arrayListOf()

        for (i : Int in 0..MAX_LAYER_LEVEL) {
            actions.add(EnableGtdLayerLevelConditionAction(i))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}