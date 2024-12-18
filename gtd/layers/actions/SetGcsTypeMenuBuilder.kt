package org.freeplane.features.gtd.layers.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.layers.GcsType.*


object SetGcsTypeMenuBuilder : EntryVisitor {
    const val key = "SET_GCS_TYPE_MENU_BUILDER"

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val actions: MutableCollection<AFreeplaneAction> = arrayListOf()

        val allowedTypes = arrayListOf<GcsType>().apply {
            add(DIRECTION)
            add(SUBDIRECTION)
            add(LOCAL)
        }

        allowedTypes.forEach {
            actions.add(SetGcsTypeAction(it))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}