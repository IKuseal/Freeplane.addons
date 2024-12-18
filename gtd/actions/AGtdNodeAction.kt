package org.freeplane.features.gtd.actions

import org.freeplane.core.ui.AFreeplaneAction
import org.freeplane.features.gtd.core.GtdController
import org.freeplane.features.custom.workspread.WorkSpreadController
import org.freeplane.features.gtd.node.GtdNodeModel
import org.freeplane.features.gtd.node.gtdNode
import java.awt.event.ActionEvent
import javax.swing.ImageIcon

abstract class AGtdNodeAction : AFreeplaneAction {
    constructor(key: String) : super(key)
    constructor(key: String, name : String? = null, imageIcon: ImageIcon? = null) : super(key, name, imageIcon)

    init { registerEnabledProp(GTD_IS_ACTIVE_PROP) }

    override fun actionPerformed(e: ActionEvent?) {
        actionPerformed(e, GtdController.selectedNode.gtdNode)

        WorkSpreadController.resetSpread()
    }

    abstract fun actionPerformed(e : ActionEvent?, node : GtdNodeModel)

    override fun setEnabled() {
        isEnabled = isAllPropsEnabled
    }
}