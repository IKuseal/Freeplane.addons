package org.freeplane.features.custom.actions

import org.freeplane.core.ui.AMultipleNodeAction
import org.freeplane.features.custom.map.NodeCover
import org.freeplane.features.custom.map.currentNodeCover
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.custom.workspread.WorkSpreadController
import org.freeplane.features.map.NodeModel
import java.awt.event.ActionEvent
import javax.swing.ImageIcon

abstract class AMultipleNodeCoverAction : AMultipleNodeAction {
    constructor(key: String) : super(key)
    constructor(key: String, name : String? = null, imageIcon: ImageIcon? = null) : super(key, name, imageIcon)

    override fun actionPerformed(e: ActionEvent?) {
        super.actionPerformed(e)
        WorkSpreadController.resetSpread()
    }

    override fun actionPerformed(e: ActionEvent?, node: NodeModel) {
        actionPerformed(e, node.currentNodeCover)
    }

    abstract fun actionPerformed(e : ActionEvent?, node : NodeCover)

    override fun setEnabled() {
        isEnabled = isAllPropsEnabled
    }
}