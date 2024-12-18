package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.data.elements.GtdNum
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class SetGtdStateNumAction(val num : GtdNum) : AMultipleGtdNodeCoverAction(generateKey(num), num.toString(), null) {
    override fun actionPerformed(e: ActionEvent?, gtdNode: GtdNodeCover) {
        GtdCoverIntentController.setStateNum(gtdNode, num)
    }

    companion object {
        fun generateKey(num : GtdNum) = "${SetGtdStateNumAction::class.simpleName}.${num.name}"
    }
}