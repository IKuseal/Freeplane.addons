package org.freeplane.features.logistics.delivery

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.menubuilders.generic.Entry
import org.freeplane.core.ui.menubuilders.generic.EntryAccessor
import org.freeplane.core.ui.menubuilders.generic.EntryVisitor
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.logistics.specialnodes.MailType


object SetTargetMailTypeMenuBuilder : EntryVisitor {
    const val SET_TARGET_MAIL_TYPE_MENU_BUILDER = "SET_TARGET_MAIL_TYPE_MENU_BUILDER"

    override fun shouldSkipChildren(entry: Entry?) = true

    override fun visit(target: Entry?) {
        val actions: MutableCollection<AFreeplaneAction> = arrayListOf()

        MailType.values().forEach {
            actions.add(SetTargetMailTypeAction(it))
        }

        val entryAccessor = EntryAccessor()
        actions.forEach {
            GlobalFrFacade.addActionIfNotAlreadySet(it)
            entryAccessor.addChildAction(target, it)
        }
    }
}