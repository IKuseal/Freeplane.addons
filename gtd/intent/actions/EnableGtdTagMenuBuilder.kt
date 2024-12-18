package org.freeplane.features.gtd.intent.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.tag.workBuiltinTags

object EnableGtdTagMenuBuilder : EntryVisitor {

    const val key = "EnableGtdTagMenuBuilder"

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val actions: MutableCollection<AFreeplaneAction> = arrayListOf()

        workBuiltinTags.forEach {
            actions.add(EnableGtdTagAction(it))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}