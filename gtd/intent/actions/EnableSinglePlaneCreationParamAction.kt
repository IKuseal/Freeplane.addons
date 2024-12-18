package org.freeplane.features.gtd.intent.actions

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.gtd.actions.AGtdAction
import org.freeplane.features.gtd.map.cover.GtdMapCoverController
import org.freeplane.features.gtd.map.cover.GtdTagCreationParamsAccessor
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class EnableSinglePlaneCreationParamAction()
    : AGtdAction("Gtd.EnableSinglePlaneCreationParamAction") {

    private val accessor : GtdTagCreationParamsAccessor
        get() = GtdMapCoverController.currentMapCover.tagCreationParamsAccessor

    override fun actionPerformed(e: ActionEvent?) {
        accessor.singlePlaneMode = !accessor.singlePlaneMode
    }

    override fun setSelected() {
        isSelected = accessor.singlePlaneMode
    }
}