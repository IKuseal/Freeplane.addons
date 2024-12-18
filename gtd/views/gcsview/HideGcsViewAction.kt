package org.freeplane.features.gtd.views.gcsview

import org.freeplane.features.gtd.actions.AGtdNodeCoverAction
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.views.GtdViewsController
import java.awt.event.ActionEvent

class HideGcsViewAction() : AGtdNodeCoverAction("Gtd.HideGcsViewAction") {
    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        GtdViewsController.hideGcsView()
    }
}