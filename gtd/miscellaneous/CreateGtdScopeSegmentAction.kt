package org.freeplane.features.gtd.miscellaneous

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.logistics.addressing.AddressController
import java.awt.event.ActionEvent

class CreateGtdScopeSegmentAction : AMultipleGtdNodeCoverAction("Gtd.CreateGtdScopeSegmentAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        GtdMiscellaneousController.createGtdSegment(node)
        AddressController.switchScope(node.node, true)
    }
}