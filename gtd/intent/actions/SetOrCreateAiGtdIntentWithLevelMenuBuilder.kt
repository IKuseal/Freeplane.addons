package org.freeplane.features.gtd.intent.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.gtd.data.elements.GtdNum

object SetOrCreateAiGtdIntentWithLevelMenuBuilder : EntryVisitor {

    const val key = "SetOrCreateAiGtdIntentWithLevelMenuBuilder"

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val actions: MutableCollection<AFreeplaneAction> = arrayListOf()

        GtdNum.s0s5Digits.forEach {
            actions.add(SetOrCreateAiGtdIntentWithLevelAction(it))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GtdController.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}