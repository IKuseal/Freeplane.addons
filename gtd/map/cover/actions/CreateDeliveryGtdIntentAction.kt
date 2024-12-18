package org.freeplane.features.gtd.map.cover.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.data.elements.GtdStateClass
import org.freeplane.features.gtd.intent.createNewEmptyIntent
import org.freeplane.features.gtd.layers.GcsType
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.tag.GTD_PLANE_DELIVERY
import java.awt.event.ActionEvent

class CreateDeliveryGtdIntentAction : AMultipleGtdNodeCoverAction("Gtd.CreateDeliveryGtdIntentAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        node.createNewEmptyIntent(true).apply {
            addTag(GTD_PLANE_DELIVERY)
            state = GtdStateClass.I.default
            gcsType = GcsType.GLOB
        }
    }
}