package org.freeplane.features.custom.filter.selectiveexclusion

import org.freeplane.core.ui.SelectableAction
import org.freeplane.features.custom.GlobalFrFacade
import org.freeplane.features.custom.actions.AMultipleNodeCoverAction
import org.freeplane.features.custom.filter.selectiveexclusion.FilterSelectiveExclusionController.branchExclusionFlag
import org.freeplane.features.custom.map.NodeCover
import java.awt.event.ActionEvent

@SelectableAction(checkOnPopup = true)
class SwitchBranchSelectiveExclusionFlagAction : AMultipleNodeCoverAction("Custom.SwitchBranchSelectiveExclusionFlagAction") {
    private var toMakeEnabled = false

    override fun actionPerformed(e: ActionEvent?) {
        toMakeEnabled = !isEnabledOnSelected()
        super.actionPerformed(e)
    }

    override fun actionPerformed(e: ActionEvent?, node: NodeCover) {
        node.branchExclusionFlag = toMakeEnabled
    }

    override fun setSelected() {
        isSelected = isEnabledOnSelected()
    }

    private fun isEnabledOnSelected() = GlobalFrFacade.selectedNodeC.branchExclusionFlag
}
