package org.freeplane.features.gtd.miscellaneous

import org.freeplane.features.gtd.actions.AMultipleGtdNodeCoverAction
import org.freeplane.features.gtd.node.GtdNodeCover
import java.awt.event.ActionEvent

class CreateGtdSegmentAction : AMultipleGtdNodeCoverAction("Gtd.CreateGtdSegmentAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        GtdMiscellaneousController.createGtdSegment(node)
    }
}