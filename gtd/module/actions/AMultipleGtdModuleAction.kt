package org.freeplane.features.gtd.module.actions

import org.freeplane.features.gtd.actions.AMultipleGtdNodeAction
import org.freeplane.features.gtd.module.GtdModule
import org.freeplane.features.gtd.node.GtdNodeModel
import java.awt.event.ActionEvent
import javax.swing.ImageIcon

abstract class AMultipleGtdModuleAction : AMultipleGtdNodeAction {
    constructor(key: String) : super(key)
    constructor(key: String, name : String? = null, imageIcon: ImageIcon? = null) : super(key, name, imageIcon)

    override fun actionPerformed(e: ActionEvent?, node: GtdNodeModel) {
        node.module?.let { actionPerformed(node, node.module!!) }
    }

    protected abstract fun actionPerformed(gtdNode : GtdNodeModel, module : GtdModule)
}