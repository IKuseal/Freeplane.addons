package org.freeplane.features.logistics.delivery

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.logistics.specialnodes.MailType
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SetTargetMailTypeAction(val mailType : MailType) :
    AFreeplaneAction(generateKey(mailType), mailType.name, null) {

    override fun actionPerformed(e: ActionEvent?) {
        DeliveryController.targetMailType = mailType
    }

    companion object {
        fun generateKey(mailType: MailType) = "${SetTargetMailTypeAction::class.simpleName}.${mailType.name}"
    }

    override fun setSelected() {
        isSelected = DeliveryController.targetMailType == mailType
    }
}