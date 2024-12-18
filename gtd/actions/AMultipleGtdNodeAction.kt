package org.freeplane.features.gtd.actions

import org.freeplane.core.ui.AMultipleNodeAction
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.gtdNode
import org.freeplane.features.custom.workspread.WorkSpreadController
import org.freeplane.features.map.NodeModel
import java.awt.event.ActionEvent
import javax.swing.ImageIcon

abstract class AMultipleGtdNodeAction : AMultipleNodeAction {
    constructor(key: String) : super(key)
    constructor(key: String, name : String? = null, imageIcon: ImageIcon? = null) : super(key, name, imageIcon)

    init { registerEnabledProp(GTD_IS_ACTIVE_PROP) }

    override fun actionPerformed(e: ActionEvent?) {
        super.actionPerformed(e)
        WorkSpreadController.resetSpread()
    }

    override fun actionPerformed(e: ActionEvent?, node: NodeModel) {
        actionPerformed(e, node.gtdNode)
    }

    abstract fun actionPerformed(e : ActionEvent?, node : GtdNodeModel)

    override fun setEnabled() {
        isEnabled = isAllPropsEnabled
    }
}