package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.data.elements.GtdStateClass
import org.freeplane.features.gtd.intent.GtdCoverIntentController
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class SetGtdStateClassAction(val clazz : GtdStateClass)
    : AMultipleGtdNodeCoverAction(generateKey(clazz), clazz.name) {

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        GtdCoverIntentController.setStateClassCreateOnNoIntent(node, clazz)
    }

    companion object {
        private fun generateKey(clazz: GtdStateClass) =
            "${SetGtdStateClassAction::class.simpleName}.${clazz.name}"
    }
}