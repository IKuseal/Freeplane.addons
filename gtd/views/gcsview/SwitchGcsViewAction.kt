package org.freeplane.features.gtd.views.gcsview

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AGtdNodeCoverAction
import org.freeplane.features.gtd.node.GtdNodeCover
import org.freeplane.features.gtd.views.GtdViewsController
import org.freeplane.features.gtd.views.gcsview.ShowGcsViewAction.GcsViewStrategy.CLOSEST
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchGcsViewAction() : AGtdNodeCoverAction("Gtd.SwitchGcsViewAction") {
    private val showAction = ShowGcsViewAction(CLOSEST)
    private val hideAction = HideGcsViewAction()

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeCover) {
        if(!isShown) showAction.actionPerformed(e, node)
        else hideAction.actionPerformed(e, node)
    }

    override fun setSelected() {
        isSelected = isShown
    }

    private val isShown get() = GtdViewsController.isGcsViewShown
}